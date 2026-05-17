package dev.sudarsan.onehelper.config;

import dev.sudarsan.onehelper.modification.config.ModificationConfig;

import java.util.LinkedHashMap;
import java.util.List;

public class ProjectConfig {
    private LinkedHashMap<String, List<ModificationConfig>> setups;

    public LinkedHashMap<String, List<ModificationConfig>> getSetups() {
        return setups;
    }
}
