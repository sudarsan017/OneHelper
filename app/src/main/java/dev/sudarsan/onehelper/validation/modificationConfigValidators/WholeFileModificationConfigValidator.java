package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.WholeFileModificationConfig;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

public class WholeFileModificationConfigValidator extends FileBasedModificationConfigValidator<WholeFileModificationConfig> {
    @Override
    public void validate(WholeFileModificationConfig modificationConfig) throws ValidationException {
        validateFilePath(modificationConfig);
        validateSourceFilePath(modificationConfig.getSourcePath());
    }

    private void validateSourceFilePath(String sourcePath) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(sourcePath)) {
            throw new ValidationException("Source path cannot be null or empty");
        }
    }
}
