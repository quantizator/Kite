package test.common.domain;

/**
 * Интерфейс актуализатора состояния агрегата на основе события предметной
 * области
 *
 * @author dmste
 */
public interface EventBasedAggregateUpdater<A extends DomainAggregate>{

    default void update(A aggregate, DomainEvent event) {
        aggregate.apply(event);
    }
}
