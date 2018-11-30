package test.common.domain;

/**
 * @author dmste
 */
public interface IMementoBuilder<A> {

    Memento backup(A entity);

    A restore(Memento memento);
}
