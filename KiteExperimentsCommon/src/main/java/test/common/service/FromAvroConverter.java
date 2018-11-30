package test.common.service;

import org.apache.avro.specific.SpecificRecord;

/**
 * @author dmste
 */
public interface FromAvroConverter<R extends SpecificRecord, O> {

    O fromAvro(R object);

    Class<R> recordType();
}
