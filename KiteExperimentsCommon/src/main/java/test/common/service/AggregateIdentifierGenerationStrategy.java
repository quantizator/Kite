package test.common.service;

import test.common.domain.AggregateIdentifier;

/**
 * @author dmste
 */
public interface AggregateIdentifierGenerationStrategy<I extends AggregateIdentifier> {

    /**
     * Генерирует следующее уникальное значение идентификатора агрегата.
     *
     * @return следующее уникальное значение
     */
    I generate();

    /**
     *
     * @return
     */
    String aggregateName();

}
