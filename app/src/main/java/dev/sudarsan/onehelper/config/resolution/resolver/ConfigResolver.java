package dev.sudarsan.onehelper.config.resolution.resolver;

import dev.sudarsan.onehelper.config.resolution.input.ResolutionInput;

import java.util.Map;

public interface ConfigResolver {
    boolean supports(ResolutionInput input, String config);
    void resolveConfig(ResolutionInput resolutionInput, Map<String, String> configValueMap);
}
