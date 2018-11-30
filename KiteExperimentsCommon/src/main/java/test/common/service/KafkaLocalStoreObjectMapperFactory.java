package test.common.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author dmste
 */
public class KafkaLocalStoreObjectMapperFactory {

    private KafkaLocalStoreObjectMapperFactory() {
    }

    /**
     * Создает ObjectMapper для использования при манипуляции с объектами,
     * которые хранятся в локальном хранилище процессоров Kafka Streams
     *
     * @return
     */
    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping();
        return objectMapper;
    }
}
