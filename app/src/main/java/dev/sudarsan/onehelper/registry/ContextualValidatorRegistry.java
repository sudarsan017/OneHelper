package dev.sudarsan.onehelper.registry;

import dev.sudarsan.onehelper.config.ResolutionInput;
import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.validation.contextual.*;

import java.util.HashMap;
import java.util.Map;

public class ContextualValidatorRegistry {
    private final Map<Class<?>, ContextualValidator<?>> registry = new HashMap<>();

    public ContextualValidatorRegistry(ResolutionInput resolutionInput){
        registry.put(LineBasedModificationConfig.class, new LineValidator());
        registry.put(WholeFileModificationConfig.class, new WholeFileValidator());
        registry.put(IntellijIdeConfigModificationConfig.class, new IntellijValidator(resolutionInput));
        registry.put(GitPatchModificationConfig.class, new GitValidator());
    }

    @SuppressWarnings("unchecked")
    public <T extends ModificationConfig> ContextualValidator<T> getValidator(T config){
        return (ContextualValidator<T>) registry.get(config.getClass());
    }

}
