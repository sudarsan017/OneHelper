package dev.sudarsan.onehelper.modification;

import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class WholeFileModification implements Modification {
    private final String sourcePath;
    private final String targetPath;

    public WholeFileModification(String sourcePath, String targetPath) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        Path targetFile = context.resolveFile(targetPath);
        Path sourceFile = Paths.get(sourcePath);

        replaceFile(sourceFile, targetFile);
    }

    private void replaceFile(Path sourceFile, Path targetFile) throws ModificationException {
        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ModificationException("Error copying the file from " + sourceFile + " to " + targetFile + ": " + e.getMessage());
        }
    }
}
