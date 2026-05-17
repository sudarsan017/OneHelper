package dev.sudarsan.onehelper.validation;

import dev.sudarsan.onehelper.config.ResolutionInput;
import dev.sudarsan.onehelper.exception.ValidationException;

public class ResolutionInputValidator {
    public static void validate(ResolutionInput resolutionInput) throws ValidationException {
        PortConfigValidator.validate(resolutionInput.getPorts());
    }
}
