package dev.sudarsan.onehelper.modification.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GitPatchModificationConfig extends BaseModificationConfig {
    private final String sourcePath;

    @JsonCreator
    public GitPatchModificationConfig(@JsonProperty("sourcePath") String sourcePath,
                                      @JsonProperty("enabled") Boolean enabled) {
        super(enabled);
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }
}
