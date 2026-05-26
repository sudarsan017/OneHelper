package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;
import dev.sudarsan.onehelper.validation.Validator;

public interface ModificationConfigValidator<T extends ModificationConfig> extends Validator<T> {
    void validate(T modificationConfig) throws ValidationException;
}
