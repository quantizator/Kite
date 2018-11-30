package test.documents.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import test.common.domain.*;
import test.documents.domain.events.DocumentFieldOptionAddedEvent;
import test.documents.domain.events.DocumentFieldRegisteredEvent;
import test.documents.domain.events.DocumentFieldUnregisteredEvent;
import test.documents.domain.events.DocumentTypeCreatedEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AggregateMetadata(name = AggregateNames.DOCUMENT_TYPES)
public class DocumentType extends DomainAggregate<DocumentTypeId> {


    public enum Status {
        DRAFT, ACTIVE, INACTIVE
    }

    private DocumentTypeCode documentTypeCode;
    private DocumentArtifactMetadata metadata;

    private AttachmentPolicy attachmentPolicy;
    private Map<DocumentFieldCode, DocumentFieldDefinition> documentFields = new HashMap<>();

    private Status status;

    public DocumentType(EventPublisher publisher, DocumentTypeId identifier, AggregateVersion version,
                        DocumentTypeCode documentTypeCode, DocumentArtifactMetadata metadata, Status status,
                        AttachmentPolicy attachmentPolicy) {
        super(publisher, identifier, version);
        this.documentTypeCode = documentTypeCode;
        this.metadata = metadata;
        this.attachmentPolicy = attachmentPolicy;
        this.status = status;
    }

    public DocumentType(EventPublisher publisher, DocumentTypeId id, DocumentTypeCode code,
                        DocumentArtifactMetadata metadata, AttachmentPolicy attachmentPolicy) {
        super(publisher, id);
        this.documentTypeCode = code;
        this.metadata = metadata;
        this.attachmentPolicy = attachmentPolicy;
        this.status = Status.DRAFT;

        done();
    }



    /**
     * @param fieldCode
     * @param fieldType
     * @param required
     * @param metadata
     */
    public boolean registerDocumentField(DocumentFieldCode fieldCode,
                                         DocumentFieldType fieldType,
                                         boolean required,
                                         DocumentArtifactMetadata metadata) {
        if (documentFields.containsKey(fieldCode)) {
            log.warn("Field with code [{0}] already exists", fieldCode);
            return false;
        }


        raiseEvent(new DocumentFieldRegisteredEvent(fieldCode, fieldType,
                required,
                metadata));

        return true;
    }

    /**
     * @param fieldCode
     * @param fieldType
     * @param required
     * @param metadata
     * @param options
     */
    public boolean registerDocumentFieldWithOptions(DocumentFieldCode fieldCode,
                                         DocumentFieldType fieldType,
                                         boolean required,
                                         DocumentArtifactMetadata metadata,
                                                    List<String> options) {
        if (documentFields.containsKey(fieldCode)) {
            log.warn("Field with code [{0}] already exists", fieldCode);
            return false;
        }


        raiseEvent(new DocumentFieldRegisteredEvent(fieldCode, fieldType,
                required,
                metadata, options));

        return true;
    }


    /**
     * @param fieldCode
     * @return
     */
    public boolean unregisterDocumentField(DocumentFieldCode fieldCode) {
        if (!documentFields.containsKey(fieldCode)) {
            log.warn("Field with code [{0}] doesn't exist for document",
                    fieldCode);
            return false;
        }

        raiseEvent(new DocumentFieldUnregisteredEvent(fieldCode));

        return true;
    }

    void addDocumentField(DocumentFieldDefinition field) {
        documentFields.put(field.fieldCode(), field);
    }

    /**
     * @param fieldCode
     * @return
     */
    public DocumentFieldDefinition getDocumentField(DocumentFieldCode fieldCode) {
        return documentFields.get(fieldCode);
    }

    public Collection<DocumentFieldDefinition> getAllDocumentFields() {
        return documentFields.values();
    }

    @Override
    public DocumentTypeCreatedEvent createInitialEvent() {
        return new DocumentTypeCreatedEvent(documentTypeCode, metadata, attachmentPolicy);
    }

    public Status status() { return status; }
    /**
     * Проверяет, готов ли тип документа к использованию в системе.
     *
     * @return готов ли тип документа к использованию
     */
    public boolean isReadyToUse() {
        return Status.ACTIVE == status();
    }

    public List<DocumentFieldCode> getRequiredFieldCodes() {
        return documentFields.entrySet().stream()
                .filter(e -> e.getValue().isRequired())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * @param fieldRegistered
     */
    public void on(DocumentFieldRegisteredEvent fieldRegistered) {
        DocumentFieldCode fieldCode = fieldRegistered.fieldCode();
        DocumentFieldType fieldType = fieldRegistered.fieldType();
        boolean isRequired = fieldRegistered.isRequired();
        DocumentArtifactMetadata metadata = fieldRegistered.metadata();
        List<String> options = fieldRegistered.options();

        final DocumentFieldDefinition field;
        if (CollectionUtils.isEmpty(options)) {
            field = new DocumentFieldDefinition(this, fieldCode, fieldType,
                    isRequired, metadata);
        } else {
            field = new DocumentFieldDefinition(this, fieldCode, isRequired,
                    metadata, options);
        }
        documentFields.put(fieldCode, field);

        updateStatus();
    }

    public void on(DocumentFieldUnregisteredEvent fieldUnregistered) {
        documentFields.remove(fieldUnregistered.fieldCode());

        updateStatus();
    }

    private void updateStatus() {
        if (this.status == Status.INACTIVE) {
            return;
        }

        this.status = documentFields == null || documentFields.isEmpty() ?
                Status.DRAFT : Status.ACTIVE;
    }


    /**
     * @param optionAdded
     */
    public void on(DocumentFieldOptionAddedEvent optionAdded) {
        String fieldCode = optionAdded.fieldCode();
        DocumentFieldDefinition field =
                documentFields.get(new DocumentFieldCode(fieldCode));
        field.options().addOption(new DocumentFieldOption(optionAdded.order(),
                optionAdded.value(), optionAdded.description()));
    }

    public DocumentTypeCode documentTypeCode() {
        return documentTypeCode;
    }

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
}
