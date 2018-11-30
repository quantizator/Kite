package test.common.domain;

/**
 * @author dmste
 */
public interface IDomainEventHandler<E extends DomainEvent>  {
        void handle(E event);
}
