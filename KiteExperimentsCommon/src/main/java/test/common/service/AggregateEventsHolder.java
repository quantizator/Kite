package test.common.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.common.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmste
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateEventsHolder {

    private DomainEvent initialEvent;
    private int version;
    private boolean shadow;
    private List<DomainEvent> events = new ArrayList<>();

    public void addEvent(DomainEvent event) {
        events.add(event);
    }

    public String aggregateName() {
        return initialEvent.aggregateName();
    }
}
