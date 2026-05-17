package dev.sudarsan.onehelper.app;

import dev.sudarsan.onehelper.config.RuntimeConfig;
import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.JsonLoadingException;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.io.Console;
import dev.sudarsan.onehelper.io.RuntimeConfigLoader;
import dev.sudarsan.onehelper.io.ToolRootResolver;
import dev.sudarsan.onehelper.io.UserInputHandler;
import dev.sudarsan.onehelper.validation.RuntimeConfigValidator;

import java.nio.file.Path;

public class App {
    public static void start(String[] args) {
        try {
            // Resources directory
            Path resourcesDirectory = ToolRootResolver.getResourcesDirectory();

            // Load json
            RuntimeConfig runtimeConfig = RuntimeConfigLoader.loadRuntimeConfig(resourcesDirectory);

            // Validate runtimeConfig
            RuntimeConfigValidator.validate(runtimeConfig);

            // just a commit message

            // Ask for the input
            UserSelection selection = UserInputHandler.getUserSelection(runtimeConfig.getRootConfig(), args);

            //Project path
            Path projectRoot = UserInputHandler.getProjectRoot(selection);

            // Project context
            ProjectContext context = new ProjectContext(projectRoot, resourcesDirectory);

            // Pass to the engine
            Engine engine = new Engine(runtimeConfig);
            engine.run(selection, context);
        } catch (ValidationException e) {
            Console.error("JSON validation failed: " + e.getMessage());
        } catch (ModificationException e) {
            Console.error("Modification failed:" + e.getMessage());
        } catch (JsonLoadingException e) {
            Console.error("Failed to load Json Configuration: " + e.getMessage());
        } catch (Exception e) {
            Console.error("Unexpected error: " + e.getMessage());
        }
    }
}
