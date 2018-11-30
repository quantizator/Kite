package test.common.service;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import test.common.domain.DomainEvent;

import java.util.List;

/**
 * @author dmste
 */
public interface KafkaEventProcessor {

    String OUTGOING = "events-outgoing";
    String INCOMING = "events-incoming";
    String PROJECTING = "events-projecting";

    String EVENTS_ERRORS = "events-errors";
    String ERROR_OUTGOING = "events-errors-outgoing";
    String ERROR_INCOMING = "events-errors-incoming";

    /**
     * Канал исходящих сообщений процессора.
     *
     * @return исходящий канал
     */
    @Output(OUTGOING)
    MessageChannel outgoing();

    /**
     * Канал (стрим) входящих сообщений процессора
     *
     * @return входящий канал
     */
    @Input(INCOMING)
    KStream<String, SpecificRecord> incoming();

    /**
     * Проекция (лог изменений), сгруппированный ко ученикам
     *
     * @return проекция
     */
    @Input(PROJECTING)
    KTable<String, List<DomainEvent>> projecting();


    @Input(EVENTS_ERRORS)
    SubscribableChannel eventErrors();

//    @Output(ERROR_OUTGOING)
//    MessageChannel errorOutgoing();
//
//    @Input(ERROR_INCOMING)
//    SubscribableChannel errorIncoming();
}
