package test.requests.domain.events;

import lombok.NoArgsConstructor;
import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DomainEvent;
import test.requests.domain.EventTypes;
import test.requests.domain.PassportData;
import test.requests.domain.SNILS;

@NoArgsConstructor
public class PassportDataSpecifiedEvent extends DomainEvent<SNILS> {

    private PassportData passportData;

    public PassportDataSpecifiedEvent(PassportData passportData) {
        this.passportData = passportData;
    }

    public PassportDataSpecifiedEvent(long date, String uuid, SNILS identifier, AggregateVersion version, PassportData passportData) {
        super(date, uuid, identifier, version);
        this.passportData = passportData;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.INDIVIDUALS;
    }

    @Override
    public String eventType() {
        return EventTypes.PASSPORT_DATA_SPECIFIED;
    }

    public PassportData passportData() {
        return passportData;
    }


}
