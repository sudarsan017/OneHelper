package dev.sudarsan.onehelper.modification.factory;

import dev.sudarsan.onehelper.config.model.Configuration;
import dev.sudarsan.onehelper.config.resolution.input.ResolutionInput;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.modification.runtime.*;
import dev.sudarsan.onehelper.strategy.CommentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModificationFactory {
    private final Map<String, CommentStrategy> commentStrategyMap;
    private final ResolutionInput resolutionInput;

    public ModificationFactory(Map<String, CommentStrategy> commentStrategyMap, ResolutionInput resolutionInput) {
        this.commentStrategyMap = commentStrategyMap;
        this.resolutionInput = resolutionInput;
    }

    public List<Modification> getModifications(List<ModificationConfig> modificationConfigs) throws ModificationException {
        List<Modification> modifications = new ArrayList<>();

        for (ModificationConfig modificationConfig : modificationConfigs) {
            if (!modificationConfig.isEnabled()) {
                continue;
            }

            modifications.add(getModification(modificationConfig));
        }

        return modifications;
    }

    private Modification getModification(ModificationConfig modificationConfig) throws ModificationException {
        if (modificationConfig instanceof LineBasedModificationConfig) {
            return getLineBasedModification(modificationConfig);
        } else if (modificationConfig instanceof WholeFileModificationConfig) {
            return getWholeFileModification(modificationConfig);
        } else if (modificationConfig instanceof IntellijIdeConfigModificationConfig) {
            return getIntellijConfigModification(modificationConfig);
        } else if (modificationConfig instanceof GitPatchModificationConfig) {
            return getGitPatchModification(modificationConfig);
        } else {
            throw new ModificationException("Unsupported modification config type: " + modificationConfig.getClass().getName());
        }
    }

    private Modification getIntellijConfigModification(ModificationConfig modificationConfig) throws ModificationException {
        IntellijIdeConfigModificationConfig intellijIdeConfigModificationConfig = (IntellijIdeConfigModificationConfig) modificationConfig;
        List<Configuration> enabledConfigurations = intellijIdeConfigModificationConfig.getConfigurations().stream().filter(Configuration::isEnabled).toList();

        return new IntellijIdeConfigModification(intellijIdeConfigModificationConfig.getFilePath(), resolutionInput, enabledConfigurations);
    }

    private Modification getGitPatchModification(ModificationConfig modificationConfig) {
        GitPatchModificationConfig gitPatchModificationConfig = (GitPatchModificationConfig) modificationConfig;

        return new GitPatchModification(gitPatchModificationConfig.getSourcePath());
    }

    private Modification getWholeFileModification(ModificationConfig modificationConfig) {
        WholeFileModificationConfig wholeFileModificationConfig = (WholeFileModificationConfig) modificationConfig;

        return new WholeFileModification(wholeFileModificationConfig.getSourcePath(), wholeFileModificationConfig.getFilePath());
    }

    private Modification getLineBasedModification(ModificationConfig modificationConfig) {
        LineBasedModificationConfig lineBasedModificationConfig = (LineBasedModificationConfig) modificationConfig;

        String path = lineBasedModificationConfig.getFilePath();
        return new LineBasedModification(path, lineBasedModificationConfig.getChanges(), getCommentStrategy(path));
    }

    private CommentStrategy getCommentStrategy(String path) {
        String fileType = path.substring(path.lastIndexOf('.') + 1);
        return commentStrategyMap.get(fileType);
    }
}
