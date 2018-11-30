package test.common.domain;

/**
 * @author dmste
 */
public interface IAggregateMementoBuilder<A extends DomainAggregate> extends IMementoBuilder<A> {
    String getIdentifierKey();

    String getVersionKey();
}
