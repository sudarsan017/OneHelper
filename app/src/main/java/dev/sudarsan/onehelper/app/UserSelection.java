package dev.sudarsan.onehelper.app;

public class UserSelection {
    private final String project;
    private final String setup;
    private final String projectPath;

    public UserSelection(String project, String setup, String projectPath) {
        this.project = project;
        this.setup = setup;
        this.projectPath = projectPath;
    }

    public UserSelection(String project) {
        this.project = project;
        this.setup = null;
        this.projectPath = null;
    }

    public UserSelection(String project, String setup) {
        this.project = project;
        this.setup = setup;
        this.projectPath = null;
    }

    public String getProject() {
        return project;
    }

    public String getSetup() {
        return setup;
    }

    public String getProjectPath() {
        return projectPath;
    }
}
