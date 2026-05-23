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
        String resultantConfig = configValue;

        if (!resultantConfig.contains("{{")) {
            return resultantConfig;
        }

        Map<String, String> resolvedConfigMap = new HashMap<>();
        for (ConfigResolver resolver : resolvers) {
            if (resolver instanceof PortResolver && !arePortsNeed(resultantConfig, resolutionInput.getPorts())) {
                continue;
            }

            resolver.resolveConfig(resolutionInput, resolvedConfigMap);
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

    private boolean arePortsNeed(String resultantConfig, Map<String, Integer> ports) {
        for (String portKey : ports.keySet()) {
            String placeHolder = "{{" + portKey + "}}";
            if (resultantConfig.contains(placeHolder)) {
                return true;
            }
        }
        return false;
    }
}
