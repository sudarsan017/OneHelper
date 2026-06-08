package dev.sudarsan.onehelper.io;

import dev.sudarsan.onehelper.app.UserSelection;
import dev.sudarsan.onehelper.config.model.RootConfig;
import dev.sudarsan.onehelper.exception.InvalidOptionException;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.validation.common.ValueValidator;
import dev.sudarsan.onehelper.validation.common.PathValidator;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    private static final Scanner sc = new Scanner(System.in);

    public static UserSelection getUserSelection(RootConfig rootConfig, String[] args) {
        if (args != null && args.length > 0) {
            return getUserSelectionFromArgs(rootConfig, args);
        }

        return getRuntimeUserSelection(rootConfig);
    }

    private static UserSelection getRuntimeUserSelection(RootConfig rootConfig) {
        String project = getSelectionFromList(rootConfig.getProjects().keySet().stream().toList(), "project");
        String setup = getSelectionFromList(rootConfig.getProjects().get(project).getSetups().keySet().stream().toList(), "setup");
        Path projectPath = getProjectPath();

        return new UserSelection(project, setup, projectPath);
    }

    private static Path getProjectPath() {
        System.out.print("Enter the project path: ");
        try {
            String path = sc.nextLine();
            return PathValidator.validateDirectoryPath(path);
        } catch (ValidationException e) {
            Console.error(e.getMessage());
            return getProjectPath();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: ", e);
        }
    }

    private static UserSelection getUserSelectionFromArgs(RootConfig rootConfig, String[] args) {
        String project = getSelectionFromArgsOrList(args, 0, "project", rootConfig.getProjects().keySet().stream().toList());
        String setup = getSelectionFromArgsOrList(args, 1, "setup", rootConfig.getProjects().get(project).getSetups().keySet().stream().toList());
        Path projectPath = getProjectPathFromArgs(args);

        return new UserSelection(project, setup, projectPath);
    }

    private static Path getProjectPathFromArgs(String[] args) {
        if (2 < args.length) {
            String projectPath = args[2];
            try {
                return PathValidator.validateDirectoryPath(projectPath);
            } catch (ValidationException e) {
                Console.error(e.getMessage());
            }
        }

        Console.error("Entered an invalid path");
        return getProjectPath();
    }

    private static String getSelectionFromArgsOrList(String[] args, int index, String item, List<String> list) {
        if (index < args.length) {
            String value = args[index];
            if (!ValueValidator.isNullOrEmpty(value) && list.contains(value)) {
                return value;
            }
        }

        Console.error("Invalid " + item + " value entered via args");
        return getSelectionFromList(list, item);
    }

    private static String getSelectionFromList(List<String> list, String item) {
        System.out.println("Select the " + item + ":");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ") " + list.get(i));
        }

        String selection = list.get(getOption(list.size()));
        Console.info("Selected " + item + ": " + selection);

        return selection;
    }

    public static int getOption(int size) {
        System.out.print("Enter the option: ");
        String option = sc.nextLine();

        try {
            int value = Integer.parseInt(option);
            if (value < 1 || value > size) {
                throw new InvalidOptionException("Invalid option selected");
            }

            return value - 1;
        } catch (NumberFormatException e) {
            Console.error("Invalid input. Please try again by entering a valid number");
            return getOption(size);
        } catch (InvalidOptionException e) {
            Console.error(e.getMessage());
            return getOption(size);
        }
    }
}
