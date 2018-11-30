package test.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

/**
 * Сервис запроса хранителя событий агрегата из хранилища состояний
 * Kafka Streams.
 */
@Component
@Slf4j
@EnableConfigurationProperties(KafkaStoreConfigurationProperties.class)
public class EventsHolderQueryServiceKafka {

    @Autowired
    private InteractiveQueryService queryService;

    @Autowired
    private WebClient.Builder builder;


    /**
     * Запрашивает хранитель событий агрегата из хранилища состояний
     * Kafka Streams
     *
     * @param aggregateId идентификатор агрегата
     * @param storeName название хранилища состояний
     * @return хранитель событий агрегата
     */
    public Mono<AggregateEventsHolder> getEventsHolderForAggregate(String aggregateId, String storeName) {

        HostInfo aggregateHostInfo = queryService.getHostInfo(storeName, aggregateId, Serdes.String().serializer());

        boolean isFoundLocally = queryService.getCurrentHostInfo().equals(aggregateHostInfo);

        return isFoundLocally ? retrieveFromLocalStateStore(storeName, aggregateId)
                : retrieveFromRemoteStateStore(aggregateHostInfo, storeName, aggregateId);
    }

    private Mono<AggregateEventsHolder> retrieveFromLocalStateStore(String storeName, String aggregateId) {
        ReadOnlyKeyValueStore<String, AggregateEventsHolder> localStore =
                queryService.getQueryableStore(storeName,
                        QueryableStoreTypes.keyValueStore());
        if (localStore == null) {
            return Mono.empty();
        }

        AggregateEventsHolder holder = localStore.get(aggregateId);
        return Mono.justOrEmpty(holder);
    }

    private Mono<AggregateEventsHolder> retrieveFromRemoteStateStore(HostInfo aggregateHostInfo, String storeName, String aggregateId) {

        String url = MessageFormat.format("http://{0}:{1}{2}", aggregateHostInfo.host(),
                aggregateHostInfo.port(), EventStoreConstants.KAFKA_REMOTE_STATE_STORE_PATH);

        log.debug("Querying aggregate [{}] event stream from location [{}]", aggregateId, url);

        return builder.baseUrl(url).build().get()
                .uri("/{storeName}/{aggregateId}", storeName, aggregateId)
                .retrieve().bodyToMono(AggregateEventsHolder.class);
    }
}
