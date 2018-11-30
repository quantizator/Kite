package test.regulations;

import org.springframework.context.annotation.Configuration;
import test.common.configuration.EventStoreConfiguration;
import test.common.service.DomainEventConverter;
import test.common.service.ForeignEventConverter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RegulationsEventStoreConfiguration extends EventStoreConfiguration {

    @Override
    public Set<DomainEventConverter> domainEventConverters() {
        return new HashSet<>();
    }

    @Override
    public Set<ForeignEventConverter> foreignEventConverters() {
        return new HashSet<>();
    }
}
