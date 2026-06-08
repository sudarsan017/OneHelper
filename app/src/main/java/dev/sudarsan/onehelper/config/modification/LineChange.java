package dev.sudarsan.onehelper.config.modification;

import dev.sudarsan.onehelper.config.modification.action.Action;

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
