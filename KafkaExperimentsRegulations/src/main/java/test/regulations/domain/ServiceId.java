package test.regulations.domain;

import lombok.EqualsAndHashCode;
import test.common.domain.AggregateIdentifier;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
public class ServiceId extends AggregateIdentifier {

    public ServiceId(@NotEmpty String id) {
        super(id.toUpperCase());
    }
}

