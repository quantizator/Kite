package test.common.domain;

/**
 * @author dmste
 */
public interface IPersistenceContextHolder<C> {
    C get();
}
