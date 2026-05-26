package dev.sudarsan.onehelper.validation;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator {
    private PathValidator() {
    }

    public static Path validateDirectoryPath(String path) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(path)) {
            throw new ValidationException("Path cannot be null or empty");
        }
        String strippedPath = stripQuotes(path);

        Path directoryPath = Path.of(strippedPath).toAbsolutePath().normalize();
        validateDirectory(directoryPath);

        return directoryPath;
    }

    private static String stripQuotes(String path) {
        if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("'") && path.endsWith("'"))) {
            return path.substring(1, path.length() - 1);
        }
        return path;
    }

    public static boolean validateFilePath(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File '" + path + "' does not exist");
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("Provided path '" + path + "' is supposed to be a file");
        }

        return true;
    }

    public static void validateDirectory(Path directory) throws ValidationException {
        if (!Files.exists(directory)) {
            throw new ValidationException("Cannot locate directory.");
        }

        if (!Files.isDirectory(directory)) {
            throw new ValidationException("Provided path is supposed to be directory");
        }
    }
}
