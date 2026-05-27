package dev.sudarsan.onehelper.validation.modification.statics;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.FileBasedModificationConfig;
import dev.sudarsan.onehelper.validation.common.ValueValidator;
import dev.sudarsan.onehelper.validation.core.StaticValidator;

abstract class FileBasedStaticValidator<T extends FileBasedModificationConfig> implements StaticValidator<T> {
    void validateFilePath(T config) throws ValidationException {
        if (ValueValidator.isNullOrEmpty(config.getFilePath())) {
            throw new ValidationException("File path is null or empty");
        }
    }
}
