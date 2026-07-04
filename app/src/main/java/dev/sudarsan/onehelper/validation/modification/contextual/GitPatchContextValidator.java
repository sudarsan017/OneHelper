package dev.sudarsan.onehelper.validation.modification.contextual;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ProcessRunException;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.GitPatchModificationConfig;
import dev.sudarsan.onehelper.util.ProcessResult;
import dev.sudarsan.onehelper.util.ProcessRunner;
import dev.sudarsan.onehelper.validation.core.ContextualValidator;

import java.nio.file.Files;
import java.nio.file.Path;

public class GitPatchContextValidator implements ContextualValidator<GitPatchModificationConfig> {
    @Override
    public void validate(ProjectContext context, GitPatchModificationConfig modificationConfig) throws ValidationException {
        try {
            Path targetDirectory = context.getProjectRoot();
            Path sourcePatchFile = context.resolveResourcesFile(modificationConfig.getSourcePath());

            ensureGitWorkingTree(targetDirectory);
            ensurePatchFileReadable(sourcePatchFile);
            ensurePatchAppliesOnDirectory(targetDirectory, sourcePatchFile);
        } catch (ProcessRunException e) {
            throw new ValidationException("Error occurred while validating git commands: " + e.getMessage());
        }
    }

    private void ensurePatchAppliesOnDirectory(Path targetDirectory, Path sourcePatchFile) throws ValidationException, ProcessRunException {
        ProcessResult result = ProcessRunner.run(targetDirectory, "git", "apply", "--check", "--whitespace=nowarn", sourcePatchFile.toAbsolutePath().toString());
        if (result.exitCode != 0) {
            throw new ValidationException("The patch file " + sourcePatchFile + " cannot be applied to the target directory " + targetDirectory + " (Could be a wrong patch file or repo, or the patch might have been already applied — validation failed):\n" + result.output);
        }
    }

    private void ensurePatchFileReadable(Path sourcePatchFile) throws ValidationException {
        if (!Files.isRegularFile(sourcePatchFile) || !Files.isReadable(sourcePatchFile)) {
            throw new ValidationException("The patch file " + sourcePatchFile + "is not readable or does not exist.");
        }
    }

    private void ensureGitWorkingTree(Path targetDirectory) throws ValidationException, ProcessRunException {
        ProcessResult result = ProcessRunner.run(targetDirectory, "git", "rev-parse", "--is-inside-work-tree");
        if (result.exitCode != 0 || !"true".equals(result.output.trim())) {
            throw new ValidationException("The target directory " + targetDirectory + " is not a git working tree.");
        }
    }
}
