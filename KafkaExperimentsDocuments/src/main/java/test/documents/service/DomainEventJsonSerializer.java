package test.documents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.support.serializer.JsonSerializer;
import test.common.domain.DomainEvent;
import test.common.service.KafkaLocalStoreObjectMapperFactory;

/**
 * @author dmste
 */
public class DomainEventJsonSerializer extends JsonSerializer<DomainEvent> {

    public DomainEventJsonSerializer() {
        this(KafkaLocalStoreObjectMapperFactory.createObjectMapper());
    }

    public DomainEventJsonSerializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
