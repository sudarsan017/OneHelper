package dev.sudarsan.onehelper.validation;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

import java.util.Map;

public class PortConfigValidator {
    private PortConfigValidator() {
    }

    public static void validate(Map<String, Integer> portConfigMap) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(portConfigMap)) {
            throw new ValidationException("Port config cannot be null or empty");
        }

        for (Map.Entry<String, Integer> entry : portConfigMap.entrySet()) {
            if (ValueCheckerUtil.isNullOrEmpty(entry.getKey())) {
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
