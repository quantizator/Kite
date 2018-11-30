package test.documents.domain.events;

public interface EventTypes {
    String DOCUMENT_TYPE_CREATED = "DocumentTypeCreatedEvent";
    String DOCUMENT_FIELD_REGISTERED = "DocumentFieldRegisteredEvent";
    String DOCUMENT_FIELD_UNREGISTERED = "DocumentFieldUnregisteredEvent";
    String DOCUMENT_FIELD_OPTION_ADDED = "DocumentFieldOptionAddedEvent";

    String DOCUMENT_CREATED = "DocumentCreatedEvent";
    String DOCUMENT_FIELDS_FILLED = "DocumentFieldsFilledEvent";
}
