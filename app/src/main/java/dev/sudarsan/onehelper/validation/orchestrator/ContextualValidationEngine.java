package dev.sudarsan.onehelper.validation.orchestrator;

import dev.sudarsan.onehelper.config.ResolutionInput;
import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;
import dev.sudarsan.onehelper.registry.ContextualValidatorRegistry;
import dev.sudarsan.onehelper.validation.core.ContextualValidator;

import java.util.List;

public class ContextualValidationEngine {
    private final ContextualValidatorRegistry registry;

    public ContextualValidationEngine(ResolutionInput input){
        registry = new ContextualValidatorRegistry(input);
    }

    public void validate(ProjectContext context, List<ModificationConfig> modificationConfigs) throws ValidationException {
        for (ModificationConfig config:modificationConfigs){
            ContextualValidator<ModificationConfig> validator = registry.getValidator(config);
            if (validator == null){
                throw new ValidationException("Unsupported modification config type: "+config.getClass().getName());
            }

            validator.validate(context, config);
        }
    }
}
