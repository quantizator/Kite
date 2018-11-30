package test.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.common.service.AggregateIdentifierGenerator;
import test.common.service.CommandExecutor;
import test.common.service.DomainEventDispatcher;

/**
 * @author dmste
 */

@Configuration
public abstract class ApplicationConfiguration {

    @Bean
    public CommandExecutor commandExecutor() {
        return new CommandExecutor();
    }

    @Bean
    public DomainEventDispatcher eventDispatcher() { return new DomainEventDispatcher(); }

    @Bean
    public AggregateIdentifierGenerator identifierGenerator() {
        return new AggregateIdentifierGenerator();
    }
}
