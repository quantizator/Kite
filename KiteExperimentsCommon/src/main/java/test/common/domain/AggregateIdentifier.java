package test.common.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class AggregateIdentifier {
    private String id;

    public AggregateIdentifier(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}

