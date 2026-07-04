package dev.sudarsan.onehelper.util;

import dev.sudarsan.onehelper.exception.ProcessRunException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {
    private static final long PROCESS_WAIT_TIME_SECONDS = 60;

    public static ProcessResult run(Path workingDir, String... command) throws ProcessRunException {
        try {
            ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);

            if (workingDir != null) {
                pb.directory(workingDir.toFile());
            }

            Process process = getProcess(command, pb);
            String output = getOutput(process);

            checkProcessTimeAndInterrupt(process);

            return new ProcessResult(process.exitValue(), output);
        } catch (ProcessRunException e) {
            throw new ProcessRunException("Error running the command: " + e.getMessage());
        }
    }

    private static void checkProcessTimeAndInterrupt(Process process) throws ProcessRunException {
        try {
            if (!process.waitFor(PROCESS_WAIT_TIME_SECONDS, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new ProcessRunException("Process execution timed out and was terminated");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessRunException("Process destroying was interrupted");
        }
    }

    private static String getOutput(Process process) throws ProcessRunException {
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            return output.toString();
        } catch (IOException e) {
            throw new ProcessRunException("Failed to read process output");
        }
    }

    private static Process getProcess(String[] command, ProcessBuilder pb) throws ProcessRunException {
        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new ProcessRunException("Failed to execute command: " + String.join(" ", command));
        }

        return process;
    }
}
