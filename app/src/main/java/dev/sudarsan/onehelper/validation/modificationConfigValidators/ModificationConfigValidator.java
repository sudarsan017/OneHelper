package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.BaseModificationConfig;

public interface ModificationConfigValidator<T extends BaseModificationConfig> {
    void validateModificationConfig(T modificationConfig) throws ValidationException;
}
