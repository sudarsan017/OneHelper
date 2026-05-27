package dev.sudarsan.onehelper.validation.modification.contextual;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.LineBasedModificationConfig;
import dev.sudarsan.onehelper.validation.core.ContextualValidator;

public class LineBasedContextValidator implements ContextualValidator<LineBasedModificationConfig> {
    @Override
    public void validate(ProjectContext context, LineBasedModificationConfig modificationConfig) throws ValidationException {
        context.resolveProjectFile(modificationConfig.getFilePath());
    }
}
