package dev.sudarsan.onehelper.util;

import dev.sudarsan.onehelper.dto.ProjectConfig;
import dev.sudarsan.onehelper.dto.RootConfig;
import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.entities.UserSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    private UserInputHandler() {
    }

    private static final Scanner sc = new Scanner(System.in);

    public static UserSelection getUserSelection(RootConfig config, String[] args) {
        if (args == null || args.length == 0) {
            String project = askForProjectConfig(config);
            String setup = askForSetup(config, project);

            return new UserSelection(project, setup);
        }
        return new UserSelection("", "");
    }

    private static String askForSetup(RootConfig config, String project) {
        ProjectConfig projectConfig = config.getProjects().get(project);
        List<String> setupNames = new ArrayList<>(projectConfig.getSetups().keySet());
        return askForSelectionInList(setupNames, "setup");
    }

    private static String askForProjectConfig(RootConfig config) {
        List<String> projectNames = new ArrayList<>(config.getProjects().keySet());
        return askForSelectionInList(projectNames, "project");
    }

    private static String askForSelectionInList(List<String> list, String item) {
        if (list == null || list.isEmpty()) {
            Console.error("No " + item + "s available for selection");
            System.exit(1);
            return null;
        } else if (list.size() == 1) {
            String name = list.get(0);
            Console.info("Selected the available " + item + ": " + name);
            return name;
        } else {
            Console.info("Please select a " + item + ":");
            int index = 1;

            for (String name : list) {
                System.out.println((index++) + ") " + name);
            }

            int selectedIndex = getSelectedIndex(list.size());

            Console.info("You selected the " + item + ": " + list.get(selectedIndex));
            return list.get(selectedIndex);
        }
    }

    private static int getSelectedIndex(int size) {
        System.out.println("Enter your option: ");
        try {
            int selectedIndex = Integer.parseInt(sc.nextLine());
            if (selectedIndex < 1 || selectedIndex > size) {
                Console.error("Invalid selection. Please try again");
                return getSelectedIndex(size);
            } else {
                return selectedIndex - 1;
            }
        } catch (NumberFormatException e) {
            Console.error("Invalid input. Please enter a valid number.");
            return getSelectedIndex(size);
        }
    }

    public static ProjectContext getProjectContext(UserSelection selection) {
        String path = askForProjectPath(selection.getProject());
        try {
            return new ProjectContext(path);
        } catch (IllegalArgumentException e) {
            Console.error(e.getMessage());
            return getProjectContext(selection);
        } catch (Exception e) {
            Console.error("Unexpected error: " + e.getMessage());
            return getProjectContext(selection);
        }
    }

    private static String askForProjectPath(String project) {
        System.out.print("Enter the base path of your project directory (" + project + "): ");
        return sc.nextLine();
    }

}
