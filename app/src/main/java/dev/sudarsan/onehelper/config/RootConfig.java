package dev.sudarsan.onehelper.config;

import java.util.LinkedHashMap;

public class RootConfig {
    private LinkedHashMap<String, ProjectConfig> projects;

    public LinkedHashMap<String, ProjectConfig> getProjects() {
        return projects;
    }
}
