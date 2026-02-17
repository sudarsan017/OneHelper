package dev.sudarsan.onehelper.dto;

import java.util.LinkedHashMap;

public class RootConfig {
    private LinkedHashMap<String, ProjectConfig> projects;

    public LinkedHashMap<String, ProjectConfig> getProjects() {
        return projects;
    }

    public void setProjects(LinkedHashMap<String, ProjectConfig> projects) {
        this.projects = projects;
    }
}
