package test.regulations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.common.configuration.ApplicationConfiguration;
import test.common.configuration.MongoConfiguration;
import test.common.service.IAggregateMementoService;
import test.common.service.MongoMementoService;

@Configuration
public class RegulationsConfiguration extends ApplicationConfiguration {

    @Bean("mementoMongoConfiguration")
    @ConfigurationProperties(prefix = "kite.mongo")
    public MongoConfiguration mongoConfiguration() {
        return new MongoConfiguration();
    }

    @Bean
    public IAggregateMementoService mementoService() {
        return new MongoMementoService();
    }


}
