package dev.sudarsan.onehelper.util;

public class ProcessResult {
    public final int exitCode;
    public final String output;

    ProcessResult(int exitCode, String output) {
        this.exitCode = exitCode;
        this.output = output;
    }
}
