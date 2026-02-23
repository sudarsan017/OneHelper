package dev.sudarsan.onehelper.entities;

public class UserSelection {
    private final String project;
    private final String setup;

    public UserSelection(String project, String setup) {
        this.project = project;
        this.setup = setup;
    }

    public String getProject() {
        return project;
    }

    public String getSetup() {
        return setup;
    }
}
