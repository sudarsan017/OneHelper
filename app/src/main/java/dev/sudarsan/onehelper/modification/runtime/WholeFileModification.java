package dev.sudarsan.onehelper.modification.runtime;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class WholeFileModification extends FileBasedModification {
    private final String sourcePath;

    public WholeFileModification(String sourcePath, String filePath) {
        super(filePath);
        this.sourcePath = sourcePath;
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        Path targetFile = context.resolveProjectFile(filePath);
        Path sourceFile = context.resolveResourcesFile(sourcePath);

        replaceFile(sourceFile, targetFile);
    }

    private void replaceFile(Path sourceFile, Path targetFile) throws ModificationException {
        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ModificationException("Error copying file from " + sourceFile + " to " + targetFile + ": " + e.getMessage());
        }
    }
}
