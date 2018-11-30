package test.requests.domain.events;

import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DomainEvent;
import test.requests.domain.EventTypes;
import test.requests.domain.SNILS;

public class IndividualAccountActivatedEvent extends DomainEvent<SNILS> {

    public IndividualAccountActivatedEvent() {
    }

    public IndividualAccountActivatedEvent(long date, String uuid, SNILS identifier, AggregateVersion version) {
        super(date, uuid, identifier, version);
    }

    @Override
    public String aggregateName() {
        return AggregateNames.INDIVIDUALS;
    }

    @Override
    public String eventType() {
        return EventTypes.INDIVIDUAL_ACCOUNT_ACTIVATED;
    }
}
