package dev.sudarsan.onehelper.dto;

import dev.sudarsan.onehelper.enums.Operation;

import java.util.Set;

public class Action {
    private Operation operation;
    private String value;
    private Set<Integer> occurrences;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Integer> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Set<Integer> occurrences) {
        this.occurrences = occurrences;
    }
}
