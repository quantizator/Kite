package test.common.domain;

/**
 * @author dmste
 */
public class DomainEntity<A extends DomainAggregate> {

    private A aggregate;

    protected DomainEntity(A aggregate) {
        this.aggregate = aggregate;
    }

    protected void raiseEvent(DomainEvent event) {
        aggregate.raiseEvent(event);
    }

    protected A getAggregate() {
        return aggregate;
    }
}
