package test.documents.service.eventconverters;

import org.springframework.stereotype.Component;
import test.common.domain.DocumentId;
import test.common.domain.DocumentTypeId;
import test.common.service.DomainEventConverter;
import test.documents.domain.DocumentArtifactMetadata;
import test.documents.domain.events.DocumentCreatedEvent;
import test.documents.domain.events.avro.DocumentCreated;

/**
 * @author dmste
 */
@Component
public class DocumentCreatedConverter implements DomainEventConverter<DocumentCreatedEvent, DocumentCreated> {

    @Override
    public DocumentCreated toAvro(DocumentCreatedEvent domainEvent) {
        return DocumentCreated.newBuilder()
                .setUuid(domainEvent.uuid().toString())
                .setDate(domainEvent.date().getTime())
                .setDocumentId(domainEvent.aggregateIdentifier().id())
                .setDocumentTypeId(domainEvent.documentTypeId().id())
                .setName(domainEvent.metadata().name())
                .setDescription(domainEvent.metadata().description())
                .build();
    }

    @Override
    public DocumentCreatedEvent fromAvro(DocumentCreated record) {
        return new DocumentCreatedEvent(record.getDate(), record.getUuid(),
                new DocumentId(record.getDocumentId()),
                new DocumentTypeId(record.getDocumentTypeId()),
                new DocumentArtifactMetadata(record.getName(), record.getDescription()));
    }

    @Override
    public Class<DocumentCreatedEvent> objectType() {
        return DocumentCreatedEvent.class;
    }

    @Override
    public Class<DocumentCreated> recordType() {
        return DocumentCreated.class;
    }
}
