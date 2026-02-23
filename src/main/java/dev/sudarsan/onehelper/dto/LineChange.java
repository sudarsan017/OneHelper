package dev.sudarsan.onehelper.dto;

import java.util.List;

public class LineChange {
    private String target;
    private List<Action> actions;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
