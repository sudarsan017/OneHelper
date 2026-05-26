package dev.sudarsan.onehelper.config;

import java.util.Map;

public interface ConfigResolver {
    boolean supports(ResolutionInput input, String config);
    void resolveConfig(ResolutionInput resolutionInput, Map<String, String> configValueMap);
}
