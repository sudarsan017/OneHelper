package dev.sudarsan.onehelper.validation.contextual;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.LineBasedModificationConfig;

public class LineValidator implements ContextualValidator<LineBasedModificationConfig> {
    @Override
    public void validate(ProjectContext context, LineBasedModificationConfig modificationConfig) throws ValidationException {
        context.resolveProjectFile(modificationConfig.getFilePath());
    }
}
