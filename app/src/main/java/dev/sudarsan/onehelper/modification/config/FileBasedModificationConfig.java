package dev.sudarsan.onehelper.modification.config;

public abstract class FileBasedModificationConfig extends BaseModificationConfig {
    private final String filePath;

    public FileBasedModificationConfig(String filepath, Boolean enabled) {
        super(enabled);
        this.filePath = filepath;
    }

    public String getFilePath() {
        return filePath;
    }
}
