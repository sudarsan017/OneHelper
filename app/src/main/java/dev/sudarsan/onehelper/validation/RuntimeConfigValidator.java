package dev.sudarsan.onehelper.validation;

import dev.sudarsan.onehelper.config.RuntimeConfig;
import dev.sudarsan.onehelper.exception.ValidationException;

public class RuntimeConfigValidator {
    private RuntimeConfigValidator() {
    }

    public static void validate(RuntimeConfig runtimeConfig) throws ValidationException {
        CommentStrategyValidator.validate(runtimeConfig.getCommentStrategyMap());
        ResolutionInputValidator.validate(runtimeConfig.getResolutionInput());

        RootConfigValidator rootConfigValidator = new RootConfigValidator(runtimeConfig.getCommentStrategyMap());
        rootConfigValidator.validateRootConfig(runtimeConfig.getRootConfig());
    }
}
