package dev.sudarsan.onehelper.modification.runtime;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.exception.ProcessRunException;
import dev.sudarsan.onehelper.util.ProcessResult;
import dev.sudarsan.onehelper.util.ProcessRunner;

import java.nio.file.Path;

public class GitPatchModification implements Modification {
    private final String sourcePath;

    public GitPatchModification(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        Path sourcePatchFile = context.resolveResourcesFile(sourcePath);
        Path targetDirectory = context.getProjectRoot();

        applyPatch(targetDirectory, sourcePatchFile);
    }

    private void applyPatch(Path targetDirectory, Path sourcePatchFile) throws ModificationException {
        try {
            ProcessResult result = ProcessRunner.run(targetDirectory, "git", "apply", "--whitespace=nowarn", sourcePatchFile.toAbsolutePath().toString());
            if (result.exitCode != 0) {
                throw new ModificationException("Failed to apply patch file " + sourcePatchFile + " to target directory " + targetDirectory + ":\n" + result.output);
            }
        } catch (ProcessRunException e) {
            throw new ModificationException("Error applying the patch: " + e.getMessage());
        }
    }
}
