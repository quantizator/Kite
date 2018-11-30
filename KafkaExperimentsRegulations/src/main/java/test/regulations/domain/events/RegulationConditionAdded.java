package test.regulations.domain.events;

import test.common.domain.AggregateNames;
import test.common.domain.AggregateVersion;
import test.common.domain.DomainEvent;
import test.regulations.domain.ConditionCode;
import test.regulations.domain.ConditionDefinition;
import test.regulations.domain.RegulationIdentifier;

/**
 *
 */
public class RegulationConditionAdded extends DomainEvent<RegulationIdentifier> {

    private ConditionCode conditionCode;
    private ConditionDefinition definition;
    private int order;


    public RegulationConditionAdded(ConditionCode conditionCode, ConditionDefinition definition, int order) {
        this.conditionCode = conditionCode;
        this.definition = definition;
        this.order = order;
    }

    public RegulationConditionAdded(long date, String uuid, RegulationIdentifier identifier,
                                    AggregateVersion version, ConditionCode conditionCode,
                                    ConditionDefinition definition, int order) {
        super(date, uuid, identifier, version);
        this.conditionCode = conditionCode;
        this.definition = definition;
        this.order = order;
    }

    public ConditionCode conditionCode() {
        return conditionCode;
    }

    public ConditionDefinition definition() {
        return definition;
    }

    public int order() {
        return order;
    }

    @Override
    public String aggregateName() {
        return AggregateNames.REGULATIONS;
    }

    @Override
    public String eventType() {
        return EventTypes.CONDITION_ADDED;
    }


}
