package dev.sudarsan.onehelper.app;

import java.nio.file.Path;

public class UserSelection {
    private final String project;
    private final String setup;
    private final Path projectPath;

    public UserSelection(String project, String setup, Path projectPath) {
        this.project = project;
        this.setup = setup;
        this.projectPath = projectPath;
    }

    public String getProject() {
        return project;
    }

    public String getSetup() {
        return setup;
    }

    public Path getProjectPath() {
        return projectPath;
    }

    @Override
    public String toString() {
        return "Project: " + project + "\nSetup: " + setup + "\nProject path: " + projectPath;
    }
}
