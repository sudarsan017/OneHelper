package dev.sudarsan.onehelper.config;

import dev.sudarsan.onehelper.strategy.CommentStrategy;

import java.util.Map;

public class RuntimeConfig {
    private final RootConfig rootConfig;
    private final Map<String, CommentStrategy> commentStrategyMap;
    private final ResolutionInput resolutionInput;

    public RuntimeConfig(RootConfig rootConfig, Map<String, CommentStrategy> commentStrategyMap, Map<String, Integer> portConfigMap) {
        this.rootConfig = rootConfig;
        this.commentStrategyMap = commentStrategyMap;
        this.resolutionInput = new ResolutionInput(portConfigMap);
    }

    public RootConfig getRootConfig() {
        return rootConfig;
    }

    public Map<String, CommentStrategy> getCommentStrategyMap() {
        return commentStrategyMap;
    }

    public ResolutionInput getResolutionInput() {
        return resolutionInput;
    }
}
