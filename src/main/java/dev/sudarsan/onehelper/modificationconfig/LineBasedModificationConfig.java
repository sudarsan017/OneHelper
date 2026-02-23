package dev.sudarsan.onehelper.modificationconfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sudarsan.onehelper.commmentstrategy.CommentStrategy;
import dev.sudarsan.onehelper.commmentstrategy.CommentStrategyFactory;
import dev.sudarsan.onehelper.dto.LineChange;
import dev.sudarsan.onehelper.exception.CommentStrategyException;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.modification.LineBasedModification;
import dev.sudarsan.onehelper.modification.Modification;

import java.util.List;

public class LineBasedModificationConfig extends BaseModificationConfig {
    private final List<LineChange> changes;

    @JsonCreator
    public LineBasedModificationConfig(@JsonProperty("filepath") String filePath,
                                       @JsonProperty("changes") List<LineChange> changes) {
        super(filePath);
        this.changes = changes;
    }

    public List<LineChange> getChanges() {
        return changes;
    }

    @Override
    public Modification toModification() throws ModificationException {
        String path = getFilePath();
        String fileType = path.substring(path.lastIndexOf('.') + 1).toLowerCase();

        try {
            CommentStrategy commentStrategy = CommentStrategyFactory.createCommentStrategy(fileType);
            return new LineBasedModification(getFilePath(), getChanges(), commentStrategy);
        } catch (CommentStrategyException e) {
            throw new ModificationException("Failed to create LineBasedModification: " + e.getMessage());
        }
    }
}
