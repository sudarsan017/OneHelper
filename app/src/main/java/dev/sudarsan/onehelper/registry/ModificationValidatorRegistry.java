package dev.sudarsan.onehelper.registry;

import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.strategy.CommentStrategy;
import dev.sudarsan.onehelper.validation.modificationConfigValidators.*;

import java.util.HashMap;
import java.util.Map;

public class ModificationValidatorRegistry {
    private final Map<Class<?>, ModificationConfigValidator<?>> registry = new HashMap<>();

    public ModificationValidatorRegistry(Map<String, CommentStrategy> commentStrategyMap) {
        registry.put(LineBasedModificationConfig.class, new LineBasedModificationConfigValidator(commentStrategyMap));
        registry.put(WholeFileModificationConfig.class, new WholeFileModificationConfigValidator());
        registry.put(IntellijIdeConfigModificationConfig.class, new IntellijConfigModificationConfigValidator());
        registry.put(GitPatchModificationConfig.class, new GitPatchModificationConfigValidator());
    }

    @SuppressWarnings("unchecked")
    public <T extends ModificationConfig> ModificationConfigValidator<T> getValidator(T config) {
        return (ModificationConfigValidator<T>) registry.get(config.getClass());
    }
}
