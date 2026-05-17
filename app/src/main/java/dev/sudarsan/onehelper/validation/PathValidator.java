package dev.sudarsan.onehelper.validation;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator {
    private PathValidator(){}

    public static Path validateDirectoryPath(String path){
        String strippedPath = stripQuotes(path);

        Path directoryPath = Path.of(strippedPath).toAbsolutePath().normalize();
        validateDirectory(directoryPath);

        return directoryPath;
    }

    private static String stripQuotes(String path) {
        if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("\'") && path.endsWith("\'"))){
            return path.substring(1, path.length() - 1);
        }
        return path;
    }

    public static void validateFilePath(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File '" + path + "' does not exist");
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("Provided path '" + path + "' is supposed to be a file");
        }
    }

    public static void validateDirectory(Path directory) {
        if (!Files.exists(directory)) {
            throw new IllegalStateException("Cannot locate directory.");
        }

        if (!Files.isDirectory(directory)) {
            throw new IllegalStateException("Provided path is supposed to be directory");
        }
    }
}
