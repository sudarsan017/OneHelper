package dev.sudarsan.onehelper.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sudarsan.onehelper.config.RootConfig;
import dev.sudarsan.onehelper.config.RuntimeConfig;
import dev.sudarsan.onehelper.exception.JsonLoadingException;
import dev.sudarsan.onehelper.strategy.CommentStrategy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class RuntimeConfigLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static RuntimeConfig loadRuntimeConfig(Path resourceDirectory) throws JsonLoadingException {
        RootConfig rootConfig = loadRootConfig(resourceDirectory);
        Map<String, CommentStrategy> commentStrategyMap = loadCommentStrategyMap(resourceDirectory);
        Map<String, Integer> portConfigMap = loadPortConfigMap(resourceDirectory);

        return new RuntimeConfig(rootConfig, commentStrategyMap, portConfigMap);
    }

    private static Map<String, Integer> loadPortConfigMap(Path resourceDirectory) throws JsonLoadingException {
        try{
            Path portConfigPath = resourceDirectory.resolve("ports.json");
            return mapper.readValue(portConfigPath.toFile(),
                    new TypeReference<Map<String, Integer>>() {
                    });
        } catch (IOException e) {
            throw new JsonLoadingException("Error loading the file ports.json");
        }
    }

    private static Map<String, CommentStrategy> loadCommentStrategyMap(Path resourceDirectory) throws JsonLoadingException {
        try {
            Path commentStrategyPath = resourceDirectory.resolve("comment_strategies.json");
            return mapper.readValue(commentStrategyPath.toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new JsonLoadingException("Error loading the file comment_strategies.json: " + e.getMessage());
        }
    }

    private static RootConfig loadRootConfig(Path resourceDirectory) throws JsonLoadingException {
        try {
            Path changesJsonPath = resourceDirectory.resolve("changes.json");
            return mapper.readValue(changesJsonPath.toFile(), RootConfig.class);
        } catch (IOException e) {
            throw new JsonLoadingException("Error loading the changes.json file: " + e.getMessage());
        }
    }
}
