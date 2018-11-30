package test.documents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import test.common.domain.DomainEvent;
import test.common.service.KafkaLocalStoreObjectMapperFactory;

/**
 * @author dmste
 */
public class DomainEventJsonSerde extends JsonSerde<DomainEvent> {

    public DomainEventJsonSerde() {
        this(KafkaLocalStoreObjectMapperFactory.createObjectMapper());
    }

    public DomainEventJsonSerde(Class<DomainEvent> targetType) {
        super(targetType);
    }

    public DomainEventJsonSerde(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public DomainEventJsonSerde(Class<DomainEvent> targetType, ObjectMapper objectMapper) {
        super(targetType, objectMapper);
    }

    public DomainEventJsonSerde(JsonSerializer<DomainEvent> jsonSerializer, JsonDeserializer<DomainEvent> jsonDeserializer) {
        super(jsonSerializer, jsonDeserializer);
    }
}
