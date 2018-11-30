package test.regulations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.common.configuration.ApplicationConfiguration;
import test.common.service.IAggregateMementoService;
import test.common.service.MongoMementoService;

@Configuration
public class RegulationsConfiguration extends ApplicationConfiguration {

    @Bean
    public IAggregateMementoService mementoService() {
        return new MongoMementoService();
    }


}
