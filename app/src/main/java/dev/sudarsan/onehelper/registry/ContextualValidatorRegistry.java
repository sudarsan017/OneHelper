package dev.sudarsan.onehelper.registry;

import dev.sudarsan.onehelper.config.resolution.input.ResolutionInput;
import dev.sudarsan.onehelper.modification.config.*;
import dev.sudarsan.onehelper.validation.core.ContextualValidator;
import dev.sudarsan.onehelper.validation.modification.contextual.GitPatchContextValidator;
import dev.sudarsan.onehelper.validation.modification.contextual.IntellijConfigContextValidator;
import dev.sudarsan.onehelper.validation.modification.contextual.LineBasedContextValidator;
import dev.sudarsan.onehelper.validation.modification.contextual.WholeFileContextValidator;

import java.util.HashMap;
import java.util.Map;

public class ContextualValidatorRegistry {
    private final Map<Class<?>, ContextualValidator<?>> registry = new HashMap<>();

    public ContextualValidatorRegistry(ResolutionInput resolutionInput){
        registry.put(LineBasedModificationConfig.class, new LineBasedContextValidator());
        registry.put(WholeFileModificationConfig.class, new WholeFileContextValidator());
        registry.put(IntellijIdeConfigModificationConfig.class, new IntellijConfigContextValidator(resolutionInput));
        registry.put(GitPatchModificationConfig.class, new GitPatchContextValidator());
    }

    @SuppressWarnings("unchecked")
    public <T extends ModificationConfig> ContextualValidator<T> getValidator(T config){
        return (ContextualValidator<T>) registry.get(config.getClass());
    }

}
