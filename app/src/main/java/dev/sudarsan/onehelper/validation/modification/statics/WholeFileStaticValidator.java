package dev.sudarsan.onehelper.validation.modification.statics;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.WholeFileModificationConfig;
import dev.sudarsan.onehelper.validation.common.ValueValidator;

public class WholeFileStaticValidator extends FileBasedStaticValidator<WholeFileModificationConfig> {
    @Override
    public void validate(WholeFileModificationConfig modificationConfig) throws ValidationException {
        validateFilePath(modificationConfig);
        validateSourceFilePath(modificationConfig.getSourcePath());
    }

    private void validateSourceFilePath(String sourcePath) throws ValidationException {
        if (ValueValidator.isNullOrEmpty(sourcePath)) {
            throw new ValidationException("Source path cannot be null or empty");
        }
    }
}
