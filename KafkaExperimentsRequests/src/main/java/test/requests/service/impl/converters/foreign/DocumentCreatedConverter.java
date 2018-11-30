package test.requests.service.impl.converters.foreign;

import test.common.service.ForeignEventConverter;
import test.documents.domain.events.avro.DocumentCreated;
import test.requests.domain.events.foreign.DocumentCreatedEvent;

/**
 * @author dmste
 */
public class DocumentCreatedConverter implements ForeignEventConverter<DocumentCreatedEvent, DocumentCreated> {

    @Override
    public DocumentCreatedEvent fromAvro(DocumentCreated object) {
        return new DocumentCreatedEvent(object.getDate(), object.getUuid(),
                object.getDocumentId(), object.getName(), object.getDescription());
    }

    @Override
    public Class<DocumentCreated> recordType() {
        return DocumentCreated.class;
    }
}
