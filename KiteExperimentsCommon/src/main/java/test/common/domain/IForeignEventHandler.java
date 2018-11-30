package test.common.domain;

/**
 * @author dmste
 */
public interface IForeignEventHandler<E extends ForeignEvent> {
    void handle(E event);
}
