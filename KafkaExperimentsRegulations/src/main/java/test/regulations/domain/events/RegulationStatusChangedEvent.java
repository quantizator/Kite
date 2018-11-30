package test.regulations.domain.events;

import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DomainEvent;
import test.regulations.domain.Regulation;
import test.regulations.domain.RegulationIdentifier;

import java.util.Date;

public class RegulationStatusChangedEvent extends DomainEvent<RegulationIdentifier> {

    private Regulation.Status status;
    private long changeDate;

    public RegulationStatusChangedEvent(long date, String uuid, RegulationIdentifier identifier,
                                        AggregateVersion version, Regulation.Status status) {
        super(date, uuid, identifier, version);
        this.status = status;
    }

    public RegulationStatusChangedEvent(Regulation.Status status, Date changeDate) {
        this.status = status;
        this.changeDate = changeDate.getTime();
    }

    public RegulationStatusChangedEvent(Regulation.Status status) {
        this(status, new Date());
    }

    @Override
    public String aggregateName() {
        return AggregateNames.REGULATIONS;
    }

    @Override
    public String eventType() {
        return EventTypes.REGULATION_STATUS_CHANGED;
    }

    public Regulation.Status status() {
        return status;
    }

    public Date changeDate() {
        return new Date(changeDate);
    }
}
