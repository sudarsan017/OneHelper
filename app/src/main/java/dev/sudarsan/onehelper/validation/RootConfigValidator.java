package dev.sudarsan.onehelper.validation;

import dev.sudarsan.onehelper.config.ProjectConfig;
import dev.sudarsan.onehelper.config.RootConfig;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.strategy.CommentStrategy;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;
import dev.sudarsan.onehelper.validation.modificationConfigValidators.GitPatchModificationConfigValidator;
import dev.sudarsan.onehelper.validation.modificationConfigValidators.IntellijConfigModificationConfigValidator;
import dev.sudarsan.onehelper.validation.modificationConfigValidators.LineBasedModificationConfigValidator;
import dev.sudarsan.onehelper.validation.modificationConfigValidators.WholeFileModificationConfigValidator;

import java.util.List;
import java.util.Map;

public class RootConfigValidator {
    private final LineBasedModificationConfigValidator lineConfigValidator;
    private final WholeFileModificationConfigValidator wholeFileConfigValidator;
    private final IntellijConfigModificationConfigValidator intellijConfigValidator;
    private final GitPatchModificationConfigValidator gitPatchConfigValidator;

    public RootConfigValidator(Map<String, CommentStrategy> commentStrategyMap) {
        lineConfigValidator = new LineBasedModificationConfigValidator(commentStrategyMap);
        wholeFileConfigValidator = new WholeFileModificationConfigValidator();
        intellijConfigValidator = new IntellijConfigModificationConfigValidator();
        gitPatchConfigValidator = new GitPatchModificationConfigValidator();
    }

    public void validateRootConfig(RootConfig rootConfig) throws ValidationException {
        if (rootConfig == null) {
            throw new ValidationException("Root config cannot be null");
        }

        validateProjectConfigs(rootConfig);
    }

    private void validateProjectConfigs(RootConfig rootConfig) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(rootConfig.getProjects())){
            throw new ValidationException("The configuration file is empty or has no projects defined");
        }

        for (ProjectConfig projectConfig:rootConfig.getProjects().values()){
            validateProjectConfig(projectConfig);
        }
    }

    private void validateProjectConfig(ProjectConfig projectConfig) throws ValidationException {
        if (projectConfig == null) {
            throw new ValidationException("Project config cannot be null");
        }

        validateModificationConfigs(projectConfig);
    }

    private void validateModificationConfigs(ProjectConfig projectConfig) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(projectConfig.getSetups())){
            throw new ValidationException("Project configuration must have at least one setup defined");
        }

        for (List<ModificationConfig> modificationConfigList:projectConfig.getSetups().values()){
            for (ModificationConfig modificationConfig:modificationConfigList){
                validateModificationConfig(modificationConfig);
            }
        }
    }

    private void validateModificationConfig(ModificationConfig modificationConfig) throws ValidationException {
        if (modificationConfig == null) {
            throw new ValidationException("Modification configuration cannot be null");
        }

        if (modificationConfig instanceof LineBasedModificationConfig){
            lineConfigValidator.validateModificationConfig((LineBasedModificationConfig) modificationConfig);
        } else if (modificationConfig instanceof WholeFileModificationConfig) {
            wholeFileConfigValidator.validateModificationConfig((WholeFileModificationConfig) modificationConfig);
        } else if (modificationConfig instanceof IntellijIdeConfigModificationConfig) {
            intellijConfigValidator.validateModificationConfig((IntellijIdeConfigModificationConfig) modificationConfig);
        } else if (modificationConfig instanceof GitPatchModificationConfig){
            gitPatchConfigValidator.validateModificationConfig((GitPatchModificationConfig) modificationConfig);
        } else {
            throw new ValidationException("Unknown modification config type: " + modificationConfig.getClass().getName());
        }
    }
}
