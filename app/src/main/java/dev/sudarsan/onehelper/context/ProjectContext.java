package dev.sudarsan.onehelper.context;

import dev.sudarsan.onehelper.validation.PathValidator;

import java.nio.file.Path;

public class ProjectContext {
    private final Path projectRoot;
    private final Path resourcesRoot;

    public ProjectContext(Path projectRoot, Path resourcesRoot) {
        this.resourcesRoot = resourcesRoot;
        this.projectRoot = projectRoot;
    }

    public Path resolveProjectFile(String relativePath) {
        return resolveFile(projectRoot, relativePath);
    }

    public Path resolveResourcesFile(String relativePath) {
        return resolveFile(resourcesRoot, relativePath);
    }

    private Path resolveFile(Path root, String relativePath) {
        Path path = root.resolve(relativePath);
        PathValidator.validateFilePath(path);
        return path;
    }

    public Path getProjectRoot() {
        return projectRoot;
    }
}
