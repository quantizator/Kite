package test.regulations.domain.events;

import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DocumentTypeId;
import test.common.domain.DomainEvent;
import test.regulations.domain.RegulationIdentifier;

public class QuestionaryRegisteredEvent extends DomainEvent<RegulationIdentifier> {

    private DocumentTypeId documentTypeId;

    public QuestionaryRegisteredEvent(long date, String uuid, RegulationIdentifier identifier,
                                      AggregateVersion version, DocumentTypeId documentTypeId) {
        super(date, uuid, identifier, version);
        this.documentTypeId = documentTypeId;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.REGULATIONS;
    }

    @Override
    public String eventType() {
        return EventTypes.QUESTIONARY_REGISTERED;
    }

    public QuestionaryRegisteredEvent(DocumentTypeId documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public DocumentTypeId documentTypeId() { return documentTypeId; }

}
