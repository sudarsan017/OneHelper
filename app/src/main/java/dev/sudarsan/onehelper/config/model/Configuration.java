package dev.sudarsan.onehelper.config.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {
    private final String templatePath;
    private final Boolean enabled;

    @JsonCreator
    public Configuration(@JsonProperty("templatePath") String templatePath,
                         @JsonProperty("enabled") Boolean enabled) {
        this.templatePath = templatePath;
        this.enabled = enabled != null ? enabled : true;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Boolean isEnabled() {
        return enabled;
    }
}
