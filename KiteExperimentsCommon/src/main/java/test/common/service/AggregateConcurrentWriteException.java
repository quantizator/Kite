package test.common.service;

/**
 * @author dmste
 */
public class AggregateConcurrentWriteException extends Exception {


    public AggregateConcurrentWriteException() {
    }

    public AggregateConcurrentWriteException(String message) {
        super(message);
    }
}
