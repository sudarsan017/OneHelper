package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.FileBasedModificationConfig;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

abstract class FileBasedModificationConfigValidator<T extends FileBasedModificationConfig> implements ModificationConfigValidator<T> {
    void validateFilePath(T config) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(config.getFilePath())) {
            throw new ValidationException("File path is null or empty");
        }
    }
}
