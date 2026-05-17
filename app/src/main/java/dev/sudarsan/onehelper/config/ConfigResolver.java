package dev.sudarsan.onehelper.config;

import java.util.Map;

public interface ConfigResolver {
    void resolveConfig(ResolutionInput resolutionInput, Map<String, String> configValueMap);
}
