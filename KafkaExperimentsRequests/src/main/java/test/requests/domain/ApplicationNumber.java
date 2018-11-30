package test.requests.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import test.common.domain.AggregateIdentifier;

@EqualsAndHashCode
@NoArgsConstructor
public class ApplicationNumber extends AggregateIdentifier {

    public ApplicationNumber(String id) {
        super(id);
    }

    public String number() {
        return id();
    }
}
