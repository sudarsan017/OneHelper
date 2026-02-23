package dev.sudarsan.onehelper.entities;

import dev.sudarsan.onehelper.dto.LineChange;

public class ChangeMatch {
    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public LineChange getChange() {
        return change;
    }

    public void setChange(LineChange change) {
        this.change = change;
    }

    public ChangeMatch(int occurrence, LineChange change) {
        this.occurrence = occurrence;
        this.change = change;
    }

    private int occurrence;
    private LineChange change;
}
