package dev.sudarsan.onehelper.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {
    private final String templatePath;
    private final Boolean enabled;

    @JsonCreator
    public Configuration(@JsonProperty("templatePath") String templatePath,
                         @JsonProperty("enabled") Boolean enabled) {
        this.templatePath = templatePath;
        this.enabled = enabled;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Boolean isEnabled() {
        return enabled;
    }
}
