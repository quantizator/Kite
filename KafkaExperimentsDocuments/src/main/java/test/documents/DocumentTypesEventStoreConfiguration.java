package test.documents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import test.common.configuration.EventStoreConfiguration;
import test.common.service.DomainEventConverter;
import test.common.service.ForeignEventConverter;
import test.documents.service.eventconverters.DocumentCreatedConverter;
import test.documents.service.eventconverters.DocumentFieldOptionAddedConverter;
import test.documents.service.eventconverters.DocumentFieldRegisteredConverter;
import test.documents.service.eventconverters.DocumentTypeCreatedConverter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dmste
 */
@Configuration
public class DocumentTypesEventStoreConfiguration extends EventStoreConfiguration {

    @Autowired
    private KafkaProperties kafkaProperties;

    public Set<DomainEventConverter> domainEventConverters() {
        Set<DomainEventConverter> converters = new HashSet<>();
        converters.add(new DocumentCreatedConverter());
        converters.add(new DocumentFieldOptionAddedConverter());
        converters.add(new DocumentTypeCreatedConverter());
        converters.add(new DocumentFieldRegisteredConverter());

        return converters;
    }

    public Set<ForeignEventConverter> foreignEventConverters() {
        return Collections.emptySet();
    }

}
