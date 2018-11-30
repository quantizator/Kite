package test.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import test.common.domain.*;

import java.util.List;

/**
 * @author dmste
 */
@Slf4j
@EnableBinding(KafkaEventProcessor.class)
@EnableConfigurationProperties(KafkaStoreConfigurationProperties.class)
@Component
public class EventStoreKafka implements IEventStore {

    private static final String AGGREGATE_NAME_HEADER = "aggregateName";

    @Autowired
    private KafkaStoreConfigurationProperties configProperties;

    @Autowired
    private KafkaEventProcessor processorBinding;

    @Autowired
    private EventConversionService conversionService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private EventsHolderQueryServiceKafka eventsHolderQueryService;

    @Override
    public Mono<EventStream> loadFullEventStream(String aggregateName,
                                                 AggregateIdentifier aggregateId) {
        Mono<AggregateEventsHolder> eventsHolder =
                getFromLocalStore(aggregateId);
        return buildEventStream(aggregateName, aggregateId,
                eventsHolder);
    }

    @Override
    public Mono<EventStream> loadEventStreamSinceVersion(String aggregateName, AggregateIdentifier aggregateId, AggregateVersion version) {
        throw new RuntimeException("Operation not supported!");
    }

    @Override
    public Mono<DomainEvent> getInitialEvent(String aggregateName, AggregateIdentifier identifier) {
        Mono<AggregateEventsHolder> eventsHolder =
                getFromLocalStore(identifier);
        return eventsHolder.map(h -> h.getInitialEvent());

    }

    @Override
    public Mono<LastVersionHolder> getLastVersion(String aggregateName, AggregateIdentifier identifier) {
        Mono<AggregateEventsHolder> eventsHolder =
                getFromLocalStore(identifier);
        return eventsHolder.map(h -> {
            AggregateVersion lastVersion = new AggregateVersion(h.getVersion());
            return new LastVersionHolder(lastVersion, h.isShadow());
        });
    }

    @Override
    public Mono<Void> appendToStream(String aggregateName,
                                     AggregateIdentifier aggregateId, DomainEvent event) {
        Mono<LastVersionHolder> lastVersion = getLastVersion(aggregateName,
                aggregateId);

        Mono<Boolean> result = lastVersion
                .switchIfEmpty(Mono.just(LastVersionHolder.absent()))
                .handle((holder, sink) -> {
                    if (!canAppendToStream(event, holder, sink)) {
                        return;
                    }
                    Message<SpecificRecord> eventMessage =
                            buildMessage(aggregateName,
                                    aggregateId,
                                    event);
                    if (eventMessage != null) {
                        sink.next(processorBinding.outgoing().send(eventMessage));
                    }
                    sink.complete();
                });

        return result.then();
    }

    private boolean  canAppendToStream(DomainEvent event, LastVersionHolder holder, SynchronousSink sink) {
        AggregateVersion lastVersion = holder.lastVersion();
        if (conversionService.isInitialDomainEvent(event)) {
            if (holder.isShadow()) {
                log.info("Attempting to restore aggregate [{}] from shadow state.", event.aggregateIdentifier());
            } else if (lastVersion.isGreater(AggregateVersion.absent())) {
                sink.error(new AggregateConcurrentWriteException(
                        "Unable to insert initial event for already existing aggregate"));
                return false;
            }
        } else if (holder.isShadow()) {
            sink.error(new AggregateDeletedException(event.aggregateName(),
                    event.aggregateIdentifier()));
            return false;
        }

        if (lastVersion.isGreater(event.version())) {
            sink.error(new AggregateConcurrentWriteException());
            return false;
        }

        return true;
    }

    private Message<SpecificRecord> buildMessage(String aggregateName,
                                                 AggregateIdentifier identifier,
                                                 DomainEvent event) {
        final SpecificRecord record;
        try {
            record = conversionService.toAvroRecord(event);
        } catch (EventConversionException ex) {
            throw new RuntimeException(ex);
        }

        return MessageBuilder.withPayload(record).setHeader(AGGREGATE_NAME_HEADER, aggregateName)
                .setHeader(KafkaHeaders.MESSAGE_KEY, identifier.id()).build();
    }

    @Override
    public Mono<Void> appendToStream(String aggregateName,
                                     AggregateIdentifier aggregateId,
                                     List<DomainEvent> events) {
        return
                events.stream().map(event -> appendToStream(aggregateName,
                        aggregateId,
                        event)).reduce(Mono.empty(), (a, b) -> a.then(b));
    }

    @StreamListener(KafkaEventProcessor.INCOMING)
    public void process(KStream<String,
            SpecificRecord> recordStream) {
        // Шаг 1: Обрабатываем событие и следуем дальше
        KStream<String, SpecificRecord> recordStreamProcessed =
                recordStream
                        .map((id, record) -> {
                            processEvent(id, record);
                            return new KeyValue<>(id, record);
                        });

        if (supportsLocalStore()) {
            // Шаг 2: отбираем события своего домена, группируем и помещаем в
            // локальное хранилище
            recordStreamProcessed.filter((id, record) -> conversionService.isDomainEventRecord(record))
                    .groupByKey()
                    .aggregate(AggregateEventsHolder::new,
                            (identifier,
                             record, aggregate) -> {
                                try {
                                    return updateEventsHolder(record, aggregate);
                                } catch (EventProcessorException ex) {
                                    sendToError(record, ex);
                                    return aggregate;
                                }
                            },
                            getLocalStore());
        }

    }

    private void sendToError(SpecificRecord eventRecord, EventProcessorException ex) {
        // TODO: implement DLQ
//        processorBinding.errorOutgoing().send(MessageBuilder.withPayload(eventRecord)
//                .setHeader(KafkaHeaders.DLT_EXCEPTION_MESSAGE, ex.getMessage())
//                .setHeader(KafkaHeaders.DLT_EXCEPTION_STACKTRACE, ex.getStackTrace()).build());
    }

    /**
     * Определеяет поддерживается ли текущей конфигурацией сохранение событий в
     * локальное хранилище
     *
     * @return сохранение в локальное хранилище поддерживается
     */
    private boolean supportsLocalStore() {
        return configProperties.getLocalStore().isEnable();
    }

    private AggregateEventsHolder updateEventsHolder(SpecificRecord record,
                                                     AggregateEventsHolder holder) throws EventProcessorException {
        try {
            DomainEvent event = conversionService.fromAvroRecord(record);
            holder.addEvent(event);

            if (conversionService.isInitialDomainEvent(event)) {
                holder.setInitialEvent(event);
            }
            holder.setVersion(event.version().version());
            holder.setShadow(conversionService.isShadowingDomainEvent(event));

            return holder;
        } catch (Exception ex) {
            throw new EventProcessorException(ex);
        }

    }

    @StreamListener(KafkaEventProcessor.EVENTS_ERRORS)
    private void handleError(Message<?> message) {
        log.error("В канале [{0}] произошла ошибка [{1}]",
                new Object[]{message.getHeaders().get(KafkaHeaders.DLT_ORIGINAL_TOPIC),
                        message.getHeaders().get(KafkaHeaders.DLT_EXCEPTION_MESSAGE)});
    }


    /**
     * Получает локальное хранилище данного процессора стримов
     * для хранения потока событий.
     *
     * @return
     */
    private Materialized<String, AggregateEventsHolder,
            KeyValueStore<Bytes, byte[]>>
    getLocalStore() {
        String storeName = configProperties.getLocalStore().getName();
        return Materialized.<String, AggregateEventsHolder,
                KeyValueStore<Bytes, byte[]>>as(storeName)
                .withKeySerde(Serdes.String())
                .withValueSerde(new EventHolderSerde());
    }

    private Mono<AggregateEventsHolder> getFromLocalStore(AggregateIdentifier aggregateId) {
        return eventsHolderQueryService.getEventsHolderForAggregate(aggregateId.id(),
                configProperties.getLocalStore().getName());
    }

    /**
     * Обрабатывает событие предметной области, сохраняя его в репозитории
     * событий и прогоняя через цепочку локальных слушателей.
     *
     * @param aggregateId идентификатор агрегата предметной области
     * @param eventRecord запись события предметной области
     */
    protected void processEvent(String aggregateId, SpecificRecord eventRecord) {
        try {
            Event event =
                    conversionService.fromAvroRecord(eventRecord);

            log.debug("Event fetched from store [{0}]", event);
            eventPublisher.publishEvent(event);
        } catch (EventConversionException ex) {
            log.error("Conversion of avro event record for aggregate [{}] failed. Skipping event processing", aggregateId, ex);
        }
//         запись событий в репозиторий
//        eventRepository.backup(event);
//        // прогоняем через цепочку слушателей
//        ListUtils.emptyIfNull(streamListeners).forEach(l -> l.onEvent(event));
    }


    private Mono<EventStream> buildEventStream(String aggregateName,
                                               AggregateIdentifier aggregateId,
                                               Mono<AggregateEventsHolder> eventsHolder) {

        return eventsHolder.map(h -> new EventStream(aggregateName, aggregateId,
                retrieveDomainEventsFromHolder(h),
                new AggregateVersion(h.getVersion()), h.isShadow()));
    }

    private List<DomainEvent> retrieveDomainEventsFromHolder(AggregateEventsHolder holder) {
//        return holder.getEvents().stream()
//                .map(r -> (DomainEvent) conversionService.fromAvroRecord(r)).collect(Collectors.toList());
        return holder.getEvents();
    }

}
