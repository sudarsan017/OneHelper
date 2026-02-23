package dev.sudarsan.onehelper;

import dev.sudarsan.onehelper.dto.RootConfig;
import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.entities.UserSelection;
import dev.sudarsan.onehelper.exception.ConfigLoadingException;
import dev.sudarsan.onehelper.util.ConfigLoader;
import dev.sudarsan.onehelper.util.Console;
import dev.sudarsan.onehelper.util.UserInputHandler;


public class App {
    public static void start(String[] args) {
        try {
            //Load json
            RootConfig rootConfig = ConfigLoader.loadConfig();

            //Ask for the input
            UserSelection selection = UserInputHandler.getUserSelection(rootConfig, args);

            // Get ProjectContext
            ProjectContext context = UserInputHandler.getProjectContext(selection);

            Engine engine = new Engine(rootConfig);
            engine.run(selection, context);
        } catch (ConfigLoadingException e) {
            Console.error(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            Console.error("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }
}
