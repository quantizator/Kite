package test.documents.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.common.domain.AggregateCreatedEvent;
import test.common.domain.AggregateNames;
import test.common.domain.DocumentTypeId;
import test.documents.domain.AttachmentPolicy;
import test.documents.domain.DocumentArtifactMetadata;
import test.documents.domain.DocumentTypeCode;

@Setter
@Getter
@NoArgsConstructor
public class DocumentTypeCreatedEvent extends AggregateCreatedEvent<DocumentTypeId> {

    private DocumentTypeCode documentTypeCode;
    private DocumentArtifactMetadata metadata;

    private AttachmentPolicy attachmentPolicy;


    public DocumentTypeCreatedEvent(DocumentTypeCode documentTypeCode, DocumentArtifactMetadata metadata, AttachmentPolicy attachmentPolicy) {
        this.documentTypeCode = documentTypeCode;
        this.metadata = metadata;
        this.attachmentPolicy = attachmentPolicy;
    }

    public DocumentTypeCreatedEvent(long date, String uuid, DocumentTypeId identifier,
                                    DocumentTypeCode documentTypeCode, DocumentArtifactMetadata metadata,
                                    AttachmentPolicy attachmentPolicy) {
        super(date, uuid, identifier);
        this.documentTypeCode = documentTypeCode;
        this.metadata = metadata;
        this.attachmentPolicy = attachmentPolicy;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public DocumentTypeCode documentTypeCode() {
        return documentTypeCode;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public DocumentArtifactMetadata metadata() {
        return metadata;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public AttachmentPolicy attachmentPolicy() {
        return attachmentPolicy;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.DOCUMENT_TYPES;
    }

    @Override
    public String eventType() {
        return EventTypes.DOCUMENT_TYPE_CREATED;
    }

}
