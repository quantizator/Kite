package test.requests.domain.events.foreign;

import test.common.domain.ForeignEvent;

/**
 * @author dmste
 */
public class DocumentCreatedEvent extends ForeignEvent {

    private String name;
    private String description;

    public DocumentCreatedEvent(long date, String uuid, String documentId,
                                String name, String description) {
        super(date, uuid, documentId, "");
        this.name = name;
        this.description = description;
    }

    public String documentId() {
        return identity();
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public String name() {
        return name;
    }

    /**
     * Значение <поле>
     *
     * @return значение <поле>
     */
    public String description() {
        return description;
    }
}
