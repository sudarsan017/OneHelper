package dev.sudarsan.onehelper.validation.common;

import dev.sudarsan.onehelper.config.ResolutionInput;
import dev.sudarsan.onehelper.exception.ValidationException;

public class ResolutionInputValidator {
    public static void validate(ResolutionInput resolutionInput) throws ValidationException {
        PortConfigValidator.validate(resolutionInput.getPorts());
    }
}
