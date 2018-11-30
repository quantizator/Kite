package test.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dmste
 */
@Setter
@Getter
public abstract class AggregateCreatedEvent<I extends AggregateIdentifier> extends DomainEvent<I> {

    public AggregateCreatedEvent() {
    }

    public AggregateCreatedEvent(long date, String uuid, I identifier) {
        super(date, uuid, identifier, AggregateVersion.initial());
    }
}
