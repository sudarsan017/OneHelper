package dev.sudarsan.onehelper.validation.contextual;

import dev.sudarsan.onehelper.config.ConfigResolutionPipeline;
import dev.sudarsan.onehelper.config.Configuration;
import dev.sudarsan.onehelper.config.ResolutionInput;
import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.IntellijIdeConfigModificationConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IntellijValidator implements ContextualValidator<IntellijIdeConfigModificationConfig> {
    private final ResolutionInput resolutionInput;

    public IntellijValidator(ResolutionInput resolutionInput){
        this.resolutionInput = resolutionInput;
    }

    @Override
    public void validate(ProjectContext context, IntellijIdeConfigModificationConfig modificationConfig) throws ValidationException {
        for (Configuration configuration: modificationConfig.getConfigurations()){
            Path templateFile = context.resolveResourcesFile(configuration.getTemplatePath());
            validateTemplateFile(templateFile);
        }
    }

    private void validateTemplateFile(Path templateFile) throws ValidationException {
        ResolutionInput clonedResolutionInput = new ResolutionInput(resolutionInput.getPorts());
        ConfigResolutionPipeline pipeline = new ConfigResolutionPipeline(clonedResolutionInput);

        String configContent = getStringFromConfiguration(templateFile);
        String resolvedConfig = pipeline.resolve(configContent);

        if (resolvedConfig.contains("{{")){
            throw new ValidationException("The template file: "+templateFile+" cannot be resolved as there is no sufficient inputs for the resolution");
        }
    }

    private String getStringFromConfiguration(Path file) throws ValidationException {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            throw new ValidationException("Error reading the configuration template file: " + e.getMessage());
        }
    }
}
