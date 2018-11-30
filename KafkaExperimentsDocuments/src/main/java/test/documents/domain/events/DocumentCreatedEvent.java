package test.documents.domain.events;

import lombok.Getter;
import lombok.Setter;
import test.common.domain.AggregateCreatedEvent;
import test.common.domain.AggregateNames;
import test.common.domain.DocumentId;
import test.common.domain.DocumentTypeId;
import test.documents.domain.DocumentArtifactMetadata;

/**
 * @author dmste
 */
@Setter
@Getter
public class DocumentCreatedEvent extends AggregateCreatedEvent<DocumentId> {

    private DocumentTypeId documentTypeId;
    private DocumentArtifactMetadata metadata;

    public DocumentCreatedEvent(DocumentTypeId documentTypeId,
                                DocumentArtifactMetadata metadata) {
        this.documentTypeId = documentTypeId;
        this.metadata = metadata;
    }

    public DocumentCreatedEvent(long date, String uuid, DocumentId identifier
            , DocumentTypeId documentTypeId,
                                DocumentArtifactMetadata metadata) {
        super(date, uuid, identifier);
        this.documentTypeId = documentTypeId;
        this.metadata = metadata;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public DocumentTypeId documentTypeId() {
        return documentTypeId;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public DocumentArtifactMetadata metadata() {
        return metadata;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.DOCUMENTS;
    }

    @Override
    public String eventType() {
        return EventTypes.DOCUMENT_CREATED;
    }
}
