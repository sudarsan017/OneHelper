package dev.sudarsan.onehelper.dto;

import dev.sudarsan.onehelper.modificationconfig.ModificationConfig;

import java.util.LinkedHashMap;
import java.util.List;

public class ProjectConfig {
    public LinkedHashMap<String, List<ModificationConfig>> getSetups() {
        return setups;
    }

    public void setSetups(LinkedHashMap<String, List<ModificationConfig>> setups) {
        this.setups = setups;
    }

    private LinkedHashMap<String, List<ModificationConfig>> setups;
}
