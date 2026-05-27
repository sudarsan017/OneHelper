package dev.sudarsan.onehelper.validation.modification.statics;

import dev.sudarsan.onehelper.config.Configuration;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.IntellijIdeConfigModificationConfig;
import dev.sudarsan.onehelper.validation.common.ValueValidator;

import java.util.List;

public class IntellijConfigStaticValidator extends FileBasedStaticValidator<IntellijIdeConfigModificationConfig> {
    @Override
    public void validate(IntellijIdeConfigModificationConfig modificationConfig) throws ValidationException {
        validateFilePath(modificationConfig);
        validateConfigurations(modificationConfig.getConfigurations());
    }

    private void validateConfigurations(List<Configuration> configurations) throws ValidationException {
        if (ValueValidator.isNullOrEmpty(configurations)) {
            throw new ValidationException("Configurations cannot be null or empty");
        }

        for (Configuration configuration : configurations) {
            if (ValueValidator.isNullOrEmpty(configuration.getTemplatePath())) {
                throw new ValidationException("Template path is null or empty");
            }
        }
    }
}
