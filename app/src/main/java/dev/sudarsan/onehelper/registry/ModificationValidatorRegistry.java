package dev.sudarsan.onehelper.registry;

import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.strategy.CommentStrategy;
import dev.sudarsan.onehelper.validation.core.StaticValidator;
import dev.sudarsan.onehelper.validation.modification.statics.GitPatchStaticValidator;
import dev.sudarsan.onehelper.validation.modification.statics.IntellijConfigStaticValidator;
import dev.sudarsan.onehelper.validation.modification.statics.LineBasedStaticValidator;
import dev.sudarsan.onehelper.validation.modification.statics.WholeFileStaticValidator;

import java.util.HashMap;
import java.util.Map;

public class ModificationValidatorRegistry {
    private final Map<Class<?>, StaticValidator<?>> registry = new HashMap<>();

    public ModificationValidatorRegistry(Map<String, CommentStrategy> commentStrategyMap) {
        registry.put(LineBasedModificationConfig.class, new LineBasedStaticValidator(commentStrategyMap));
        registry.put(WholeFileModificationConfig.class, new WholeFileStaticValidator());
        registry.put(IntellijIdeConfigModificationConfig.class, new IntellijConfigStaticValidator());
        registry.put(GitPatchModificationConfig.class, new GitPatchStaticValidator());
    }

    @SuppressWarnings("unchecked")
    public <T extends ModificationConfig> StaticValidator<T> getValidator(T config) {
        return (StaticValidator<T>) registry.get(config.getClass());
    }
}
