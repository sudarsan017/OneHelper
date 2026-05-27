package dev.sudarsan.onehelper.validation.common;

import dev.sudarsan.onehelper.exception.ValidationException;

import java.util.Map;

public class PortConfigValidator {
    private PortConfigValidator() {
    }

    public static void validate(Map<String, Integer> portConfigMap) throws ValidationException {
        for (Map.Entry<String, Integer> entry : portConfigMap.entrySet()) {
            if (ValueValidator.isNullOrEmpty(entry.getKey())) {
                throw new ValidationException("Port name cannot be null or empty");
            }

            if (entry.getValue() == null) {
                throw new ValidationException("Default port for " + entry.getKey() + " cannot be null");
            }

            if (entry.getValue() < 0 || entry.getValue() > 65535) {
                throw new ValidationException("Default port for " + entry.getKey() + " cannot be negative");
            }
        }
    }
}
