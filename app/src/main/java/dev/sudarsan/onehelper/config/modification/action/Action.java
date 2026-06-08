package dev.sudarsan.onehelper.config.modification.action;

import java.util.Set;

public class Action {
    private Operation operation;
    private String value;
    private Set<Integer> occurrences;

    public Operation getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }

    public Set<Integer> getOccurrences() {
        return occurrences;
    }
}
