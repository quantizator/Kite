package test.requests.domain;

public interface EventTypes {
    // заявители
    String INDIVIDUAL_REGISTERED = "IndividualRegistered";
    String PASSPORT_DATA_SPECIFIED = "PassportDataSpecified";
    String INDIVIDUAL_ACCOUNT_ACTIVATED = "IndividualAccountActivated";
    String ORGANIZATION_REGISTERED = "OrganizationRegistered";

    String INDIVIDUAL_UNREGISTERED = "IndividualUnregistered";
    String ORGANIZATION_UNREGISTERED = "OrganizationUnregistered";

    // заявления
    String DRAFT_HANDED_OVER = "DraftHandedOver";
    String DOCUMENT_LINKED = "DocumentLinkedEvent";
    String DOCUMENT_REJECTED = "DocumentRejectedEvent";
    String FIELDS_FILLED = "FieldsFilled";
    String STATUS_CHANGED = "StatusChanged";
    String APPLICATION_ARCHIVED = "ApplicationArchived";
}
