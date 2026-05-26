package dev.sudarsan.onehelper.validation.contextual;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;
import dev.sudarsan.onehelper.validation.Validator;

public interface ContextualValidator<T extends ModificationConfig> extends Validator<T> {
    void validate(ProjectContext context, T modificationConfig) throws ValidationException;
}
