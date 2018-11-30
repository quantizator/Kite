package test.documents;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.common.configuration.ApplicationConfiguration;
import test.common.domain.AggregateNames;
import test.common.domain.DocumentId;
import test.common.domain.DocumentTypeId;
import test.common.service.AggregateIdentifierGenerationStrategy;
import test.common.service.IAggregateMementoService;
import test.common.service.MongoMementoService;
import test.common.service.UUIDIdentifierGenerationStrategy;

import java.util.UUID;

/**
 * @author dmste
 */
@Configuration
public class DocumentTypesConfiguration extends ApplicationConfiguration {

    @Bean
    public IAggregateMementoService mementoService() {
        return new MongoMementoService();
    }

    @Bean
    public AggregateIdentifierGenerationStrategy<DocumentTypeId> documentTypeGenerationStrategy() {
        return new UUIDIdentifierGenerationStrategy<DocumentTypeId>() {
            @Override
            public DocumentTypeId createIdentifier(UUID uuid) {
                return new DocumentTypeId(uuid.toString());
            }

            public String aggregateName() {
                return AggregateNames.DOCUMENT_TYPES;
            }
        };
    }

    @Bean
    public AggregateIdentifierGenerationStrategy<DocumentId> documentGenerationStrategy() {
        return new UUIDIdentifierGenerationStrategy<DocumentId>() {
            @Override
            public DocumentId createIdentifier(UUID uuid) {
                return new DocumentId(uuid.toString());
            }

            public String aggregateName() {
                return AggregateNames.DOCUMENTS;
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonVisibilityCustomizer() {
        return (builder) -> {
            builder.visibility(PropertyAccessor.FIELD,
                    JsonAutoDetect.Visibility.ANY);
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        };
    }
}
