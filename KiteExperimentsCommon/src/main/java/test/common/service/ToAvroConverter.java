package test.common.service;

import org.apache.avro.specific.SpecificRecord;

/**
 * @author dmste
 */
interface ToAvroConverter<O, R extends SpecificRecord> {

    R toAvro(O object);

    Class<O> objectType();
}
