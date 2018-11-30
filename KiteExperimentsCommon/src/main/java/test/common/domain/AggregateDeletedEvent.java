package test.common.domain;

/**
 * Событие удаления агрегата из системы
 *
 * @param <I>
 */
public abstract class AggregateDeletedEvent<A extends DomainAggregate<I>, I extends AggregateIdentifier> extends DomainEvent<I> {

    public AggregateDeletedEvent(A aggregate) {
        this.identifier = aggregate.identifier();
        this.version = aggregate.version().increment();
    }

    public AggregateDeletedEvent(long date, String uuid, I identifier, AggregateVersion version) {
        super(date, uuid, identifier, version);
    }
}
