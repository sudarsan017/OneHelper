package dev.sudarsan.onehelper.config;

public class ChangeMatch {
    private final int occurrence;
    private final LineChange change;

    public ChangeMatch(int occurrence, LineChange change) {
        this.occurrence = occurrence;
        this.change = change;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public LineChange getChange() {
        return change;
    }
}
