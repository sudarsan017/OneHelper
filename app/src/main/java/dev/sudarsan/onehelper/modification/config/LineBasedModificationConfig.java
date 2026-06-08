package dev.sudarsan.onehelper.modification.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sudarsan.onehelper.config.modification.LineChange;

import java.util.List;

public class LineBasedModificationConfig extends FileBasedModificationConfig {
    private final List<LineChange> changes;

    @JsonCreator
    public LineBasedModificationConfig(@JsonProperty("filePath") String filePath,
                                       @JsonProperty("enabled") Boolean enabled,
                                       @JsonProperty("changes") List<LineChange> changes) {
        super(filePath, enabled);
        this.changes = changes;
    }

    public List<LineChange> getChanges() {
        return changes;
    }

}