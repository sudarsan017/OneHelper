package dev.sudarsan.onehelper.io;

import dev.sudarsan.onehelper.validation.PathValidator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToolRootResolver {
    public static Path getResourcesDirectory() {
        Path resourcesDirectory = Paths.get("").toAbsolutePath().resolve("resources");
        PathValidator.validateDirectory(resourcesDirectory);

        return resourcesDirectory;
    }
}
