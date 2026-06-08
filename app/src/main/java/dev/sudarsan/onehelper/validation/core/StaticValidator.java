package dev.sudarsan.onehelper.validation.core;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;

public interface StaticValidator<T extends ModificationConfig> {
    void validate(T modificationConfig) throws ValidationException;
}
