package dev.sudarsan.onehelper.modification.runtime;

abstract class FileBasedModification implements Modification {
    public final String filePath;

    public FileBasedModification(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
