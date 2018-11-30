package test.requests.domain.events;

import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DomainEvent;
import test.requests.domain.ApplicationNumber;
import test.requests.domain.EventTypes;
import test.requests.domain.Status;

public class ApplicationStatusChangedEvent extends DomainEvent<ApplicationNumber> {

    private Status newStatus;

    public ApplicationStatusChangedEvent(long date, String uuid, ApplicationNumber identifier, AggregateVersion version, Status newStatus) {
        super(date, uuid, identifier, version);
        this.newStatus = newStatus;
    }

    public ApplicationStatusChangedEvent(Status newStatus) {
        this.newStatus = newStatus;
    }

    public Status newStatus() { return newStatus; }

    @Override
    public String aggregateName() {
        return AggregateNames.APPLICATIONS;
    }

    @Override
    public String eventType() {
        return EventTypes.STATUS_CHANGED;
    }

}
