package dev.sudarsan.onehelper.modification.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sudarsan.onehelper.config.model.Configuration;

import java.util.List;

public class IntellijIdeConfigModificationConfig extends IdeConfigModificationConfig {
    private final List<Configuration> configurations;

    @JsonCreator
    public IntellijIdeConfigModificationConfig(@JsonProperty("filePath") String filePath,
                                               @JsonProperty("enabled") Boolean enabled,
                                               @JsonProperty("configurations") List<Configuration> configurations) {
        super(filePath == null ? ".idea/workspace.xml" : filePath, enabled);
        this.configurations = configurations;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

}
