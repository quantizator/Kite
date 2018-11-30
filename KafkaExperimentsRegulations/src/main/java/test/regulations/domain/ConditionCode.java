package test.regulations.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ConditionCode {
    private String code;

    public ConditionCode(String code) {
        this.code = code;
    }

    public String value() { return code; }
}
