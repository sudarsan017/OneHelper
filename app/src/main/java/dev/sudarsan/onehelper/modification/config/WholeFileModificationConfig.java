package dev.sudarsan.onehelper.modification.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WholeFileModificationConfig extends FileBasedModificationConfig {
    private final String sourcePath;

    @JsonCreator
    public WholeFileModificationConfig(@JsonProperty("filePath") String filePath,
                                       @JsonProperty("enabled") Boolean enabled,
                                       @JsonProperty("sourcePath") String sourcePath) {
        super(filePath, enabled);
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }
}
