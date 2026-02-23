package dev.sudarsan.onehelper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sudarsan.onehelper.dto.RootConfig;
import dev.sudarsan.onehelper.exception.ConfigLoadingException;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    private ConfigLoader() {
    }

    public static RootConfig loadConfig() throws ConfigLoadingException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("changes.json")) {
            RootConfig rootConfig = mapper.readValue(inputStream, RootConfig.class);

            return rootConfig;
        } catch (IOException e) {
            throw new ConfigLoadingException("Error loading changes.json");
        }
    }
}
