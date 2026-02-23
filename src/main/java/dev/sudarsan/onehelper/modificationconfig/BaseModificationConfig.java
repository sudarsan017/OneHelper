package dev.sudarsan.onehelper.modificationconfig;

public abstract class BaseModificationConfig implements ModificationConfig {
    private final String filePath;

    public BaseModificationConfig(String filepath) {
        this.filePath = filepath;
    }

    public String getFilePath() {
        return filePath;
    }
}
