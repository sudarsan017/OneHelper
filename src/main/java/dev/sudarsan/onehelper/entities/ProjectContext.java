package dev.sudarsan.onehelper.entities;

import java.nio.file.Files;
import java.nio.file.Path;

public class ProjectContext {
    private final Path root;

    public ProjectContext(String basePath) {
        String strippedPath = stripQuotes(basePath);
        Path path = Path.of(strippedPath).toAbsolutePath().normalize();
        isDirectory(path);
        this.root = path;
    }

    public void isDirectory(Path root) {
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("The provided path is not a directory: " + root);
        }
    }

    private String stripQuotes(String path) {
        if ((path.startsWith("\"") && path.endsWith("\"")) || (path.startsWith("'") && path.endsWith("'"))) {
            return path.substring(1, path.length() - 1);
        }

        return path;
    }

    public Path resolveFile(String relativePath) {
        String strippedPath = stripQuotes(relativePath);
        isFile(root, strippedPath);
        return root.resolve(strippedPath);
    }

    private void isFile(Path root, String relativePath) {
        Path filepath = root.resolve(relativePath);
        if (!Files.isRegularFile(filepath)) {
            throw new IllegalArgumentException("The provided path is not a file: " + filepath);
        }
    }
}
