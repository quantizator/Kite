package test.common.domain.published;

/**
 * @author dmste
 */
public interface DomainCommand {

    /**
     * Возвращает простое имя команды.
     *
     * @return
     */
    default String commandName() {
        return getClass().getSimpleName();
    }
}
