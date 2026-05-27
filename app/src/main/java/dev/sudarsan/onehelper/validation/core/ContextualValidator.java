package dev.sudarsan.onehelper.validation.core;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;

public interface ContextualValidator<T extends ModificationConfig> extends Validator<T> {
    void validate(ProjectContext context, T modificationConfig) throws ValidationException;
}
