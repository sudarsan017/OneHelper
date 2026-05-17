package dev.sudarsan.onehelper.modification.runtime;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class GitPatchModification implements Modification {
    private static final long PROCESS_WAIT_TIME_SECONDS = 60;
    private final String sourcePath;

    public GitPatchModification(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        Path sourcePatchFile = context.resolveResourcesFile(sourcePath);
        Path targetDirectory = context.getProjectRoot();

        ensurePatchFileReadable(sourcePatchFile);
        ensureGitAvailable();
        ensureGitWorkingTree(targetDirectory);
        ensurePatchAppliesOnDirectory(targetDirectory, sourcePatchFile);
        applyPatch(targetDirectory, sourcePatchFile);
    }

    private void applyPatch(Path targetDirectory, Path sourcePatchFile) throws ModificationException {
        ProcessResult result = run(targetDirectory, "git", "apply","--whitespace=nowarn", sourcePatchFile.toAbsolutePath().toString());
        if (result.exitCode != 0) {
            throw new ModificationException("Failed to apply patch file "+sourcePatchFile + " to target directory "+targetDirectory+":\n"+result.output);
        }
    }

    private void ensurePatchAppliesOnDirectory(Path targetDirectory, Path sourcePatchFile) throws ModificationException {
        ProcessResult result = run(targetDirectory, "git", "apply", "--check", "--whitespace=nowarn", sourcePatchFile.toAbsolutePath().toString());
        if (result.exitCode != 0) {
            throw new ModificationException("The patch file " + sourcePatchFile + " cannot be applied to the target directory " + targetDirectory + " (Could be a wrong patch file or repo, or the patch might have been already applied — validation failed):\n" + result.output);
        }
    }

    private void ensureGitWorkingTree(Path targetDirectory) throws ModificationException {
        ProcessResult result = run(targetDirectory, "git", "rev-parse", "--is-inside-work-tree");
        if (result.exitCode != 0 || !"true".equals(result.output.trim())) {
            throw new ModificationException("The target directory " + targetDirectory + " is not a git working tree.");
        }

    }

    private void ensureGitAvailable() throws ModificationException {
        ProcessResult result = run(null, "git", "--version");
        if (result.exitCode != 0) {
            throw new ModificationException("Git command is not available: " + result.output);
        }
    }

    private ProcessResult run(Path workingDir, String... command) throws ModificationException {
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);

        if (workingDir != null) {
            pb.directory(workingDir.toFile());
        }

        Process process = getProcess(command, pb);
        String output = getOutput(process);

        checkProcessTimeAndInterrupt(process);

        return new ProcessResult(process.exitValue(), output);
    }

    private void checkProcessTimeAndInterrupt(Process process) throws ModificationException {
        try {
            if (!process.waitFor(PROCESS_WAIT_TIME_SECONDS, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new ModificationException("Process execution timed out and was terminated");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ModificationException("Process destroying was interrupted");
        }
    }

    private String getOutput(Process process) throws ModificationException {
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            return output.toString();
        } catch (IOException e) {
            throw new ModificationException("Failed to read process output");
        }
    }

    private Process getProcess(String[] command, ProcessBuilder pb) throws ModificationException {
        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new ModificationException("Failed to execute command: " + String.join(" ", command));
        }

        return process;
    }

    private void ensurePatchFileReadable(Path sourcePatchFile) throws ModificationException {
        if (!Files.isRegularFile(sourcePatchFile) || !Files.isReadable(sourcePatchFile)) {
            throw new ModificationException("The patch file " + sourcePatchFile + "is not readable or does not exist.");
        }
    }

    private static final class ProcessResult {
        final int exitCode;
        final String output;

        ProcessResult(int exitCode, String output) {
            this.exitCode = exitCode;
            this.output = output;
        }
    }
}
