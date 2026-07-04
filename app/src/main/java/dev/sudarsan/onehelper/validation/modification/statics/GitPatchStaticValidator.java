package dev.sudarsan.onehelper.validation.modification.statics;

import dev.sudarsan.onehelper.exception.ProcessRunException;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.GitPatchModificationConfig;
import dev.sudarsan.onehelper.util.ProcessResult;
import dev.sudarsan.onehelper.util.ProcessRunner;
import dev.sudarsan.onehelper.validation.common.ValueValidator;
import dev.sudarsan.onehelper.validation.core.StaticValidator;

public class GitPatchStaticValidator implements StaticValidator<GitPatchModificationConfig> {
    @Override
    public void validate(GitPatchModificationConfig modificationConfig) throws ValidationException {
        validateSourceFilePath(modificationConfig.getSourcePath());
        ensureGitAvailable();
    }

    private void ensureGitAvailable() throws ValidationException {
        try {
            ProcessResult result = ProcessRunner.run(null, "git", "--version");
            if (result.exitCode != 0) {
                throw new ValidationException("Git command is not available: " + result.output);
            }
        } catch (ProcessRunException e) {
            throw new ValidationException("Error occurred while validating: " + e.getMessage());
        }
    }

    private void validateSourceFilePath(String sourcePath) throws ValidationException {
        if (ValueValidator.isNullOrEmpty(sourcePath)) {
            throw new ValidationException("Source path cannot be null or empty");
        }

        if (!sourcePath.endsWith(".patch")) {
            throw new ValidationException("Source file has to be a git's .patch file");
        }
    }
}
