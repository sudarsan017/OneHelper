package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;

public interface ModificationConfigValidator<T extends ModificationConfig> {
    void validate(T modificationConfig) throws ValidationException;
}
