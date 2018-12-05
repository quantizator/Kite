package test.common.service;

/**
 * @author dmste
 */
public class AggregateConcurrentWriteException extends RuntimeException {


    public AggregateConcurrentWriteException() {
    }

    public AggregateConcurrentWriteException(String message) {
        super(message);
    }
}
