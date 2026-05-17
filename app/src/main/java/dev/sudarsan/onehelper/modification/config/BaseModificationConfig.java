package dev.sudarsan.onehelper.modification.config;

public abstract class BaseModificationConfig implements ModificationConfig {
    private final Boolean enabled;

    public BaseModificationConfig(Boolean enabled) {
        this.enabled = enabled != null ? enabled : true;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }
}
