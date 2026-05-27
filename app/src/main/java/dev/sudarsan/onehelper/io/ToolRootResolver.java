package dev.sudarsan.onehelper.io;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.validation.common.PathValidator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToolRootResolver {
    public static Path getResourcesDirectory() throws ValidationException {
        Path resourcesDirectory = Paths.get("").toAbsolutePath().resolve("resources");
        PathValidator.validateDirectory(resourcesDirectory);

        return resourcesDirectory;
    }
}
