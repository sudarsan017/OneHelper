package dev.sudarsan.onehelper.modification.config;

public abstract class IdeConfigModificationConfig extends FileBasedModificationConfig {
    public IdeConfigModificationConfig(String filePath, Boolean enabled) {
        super(filePath, enabled);
    }
}
