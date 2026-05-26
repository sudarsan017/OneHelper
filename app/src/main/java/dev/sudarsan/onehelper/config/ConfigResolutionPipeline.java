package dev.sudarsan.onehelper.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigResolutionPipeline {
    private final List<ConfigResolver> resolvers = List.of(
            new PortResolver()
    );

    private final ResolutionInput resolutionInput;

    public ConfigResolutionPipeline(ResolutionInput resolutionInput) {
        this.resolutionInput = resolutionInput;
    }

    public String resolve(String configValue) {
        if (!configValue.contains("{{")) {
            return configValue;
        }

        String resultantConfig = configValue;

        Map<String, String> resolvedConfigMap = new HashMap<>();
        for (ConfigResolver resolver : resolvers) {
            if (resolver.supports(resolutionInput, resultantConfig)) {
                resolver.resolveConfig(resolutionInput, resolvedConfigMap);
            }
        }

        if (resolvedConfigMap.isEmpty()) {
            return resultantConfig;
        }

        for (Map.Entry<String, String> entry : resolvedConfigMap.entrySet()) {
            String placeHolder = "{{" + entry.getKey() + "}}";
            resultantConfig = resultantConfig.replace(placeHolder, entry.getValue());
        }

        return resultantConfig;
    }
}
