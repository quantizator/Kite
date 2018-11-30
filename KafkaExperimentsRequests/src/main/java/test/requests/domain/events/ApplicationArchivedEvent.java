package test.requests.domain.events;

import test.common.domain.AggregateDeletedEvent;
import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.requests.domain.Application;
import test.requests.domain.ApplicationNumber;
import test.requests.domain.EventTypes;
import test.requests.domain.OperatorId;

public class ApplicationArchivedEvent extends AggregateDeletedEvent<Application, ApplicationNumber> {

    private OperatorId operator;

    public ApplicationArchivedEvent(Application aggregate, OperatorId operator) {
        super(aggregate);
        this.operator = operator;
    }

    public ApplicationArchivedEvent(long date, String uuid, ApplicationNumber identifier,
                                    AggregateVersion version,
                                    OperatorId operator) {
        super(date, uuid, identifier, version);
        this.operator = operator;
    }

    public OperatorId operator() {
        return operator;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.APPLICATIONS;
    }

    @Override
    public String eventType() {
        return EventTypes.APPLICATION_ARCHIVED;
    }
}
