package dev.sudarsan.onehelper.modificationconfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.modification.Modification;
import dev.sudarsan.onehelper.modification.WholeFileModification;

public class WholeFileModificationConfig extends BaseModificationConfig {
    private final String sourcePath;

    @JsonCreator
    public WholeFileModificationConfig(@JsonProperty("filePath") String filePath,
                                       @JsonProperty("sourcePath") String sourcePath) {
        super(filePath);
        this.sourcePath = sourcePath;
    }

    @Override
    public Modification toModification() throws ModificationException {
        return new WholeFileModification(sourcePath, getFilePath());
    }
}
