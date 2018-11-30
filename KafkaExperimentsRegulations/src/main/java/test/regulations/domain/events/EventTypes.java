package test.regulations.domain.events;

public interface EventTypes {
    String REGULATION_REGISTERED = "RegulationRegisteredEvent";
    String REGULATION_STATUS_CHANGED = "RegulationStatusChangedEvent";
    String QUESTIONARY_REGISTERED = "QuestionaryRegistered";
    String REGULATION_CANCELLED = "RegulationCancelled";
    String CONDITION_ADDED = "ConditionAdded";
    String CONDITION_OPTION_ADDED = "ConditionOptionAdded";
}
