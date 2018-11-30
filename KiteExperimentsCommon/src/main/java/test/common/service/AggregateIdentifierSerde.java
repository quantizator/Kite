package test.common.service;

import com.google.gson.reflect.TypeToken;
import test.common.domain.AggregateIdentifier;

import java.lang.reflect.Type;

/**
 * @author dmste
 */
public class AggregateIdentifierSerde extends GsonSerde<AggregateIdentifier> {
    @Override
    public Type getType() {
        return new TypeToken<AggregateIdentifier>() {
        }.getType();
    }
}
