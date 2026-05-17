package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.BaseModificationConfig;
import dev.sudarsan.onehelper.modification.config.GitPatchModificationConfig;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

public class GitPatchModificationConfigValidator extends BaseModificationConfigValidator<GitPatchModificationConfig> {
    @Override
    public void validateModificationConfig(GitPatchModificationConfig modificationConfig) throws ValidationException {
        validateSourceFilePath(modificationConfig.getSourcePath());
    }

    private void validateSourceFilePath(String sourcePath) throws ValidationException {
        if  (ValueCheckerUtil.isNullOrEmpty(sourcePath)){
            throw new ValidationException("Source path cannot be null or empty");
        }

        if (!sourcePath.endsWith(".patch")){
            throw new ValidationException("Source file has to be a git's .patch file");
        }
    }
}
