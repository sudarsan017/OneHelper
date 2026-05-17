package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.config.Configuration;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.IntellijIdeConfigModificationConfig;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

import java.util.List;

public class IntellijConfigModificationConfigValidator extends FileBasedModificationConfigValidator<IntellijIdeConfigModificationConfig> {
    @Override
    public void validateModificationConfig(IntellijIdeConfigModificationConfig modificationConfig) throws ValidationException {
        validateFilePath(modificationConfig);
        validateConfigurations(modificationConfig.getConfigurations());
    }

    private void validateConfigurations(List<Configuration> configurations) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(configurations)) {
            throw new ValidationException("Configurations cannot be null or empty");
        }

        for (Configuration configuration : configurations) {
            if (ValueCheckerUtil.isNullOrEmpty(configuration.getTemplatePath())){
                throw new ValidationException("Template path is null or empty");
            }
        }
    }
}
