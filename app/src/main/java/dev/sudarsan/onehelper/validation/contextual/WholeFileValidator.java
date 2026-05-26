package dev.sudarsan.onehelper.validation.contextual;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.WholeFileModificationConfig;

public class WholeFileValidator implements ContextualValidator<WholeFileModificationConfig> {
    @Override
    public void validate(ProjectContext context, WholeFileModificationConfig modificationConfig) throws ValidationException {
        context.resolveProjectFile(modificationConfig.getFilePath());
        context.resolveResourcesFile(modificationConfig.getSourcePath());
    }
}
