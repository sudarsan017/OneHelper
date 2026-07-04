package dev.sudarsan.onehelper.io;

public class Console {
    public static void success(String message) {
        System.out.println(ConsoleColors.GREEN + message + ConsoleColors.RESET);
    }

    private static void printMessage(String message, String color) {
        System.out.println("\n" + color + message + ConsoleColors.RESET);
    }

    public static void error(String message) {
        printMessage(message, ConsoleColors.RED);
    }

    public static void warn(String message) {
        printMessage(message, ConsoleColors.YELLOW);
    }

    public static void info(String message) {
        printMessage(message, ConsoleColors.CYAN);
    }

    public static void ask(String message) {
        System.out.print("\n" + ConsoleColors.BLUE + message + ConsoleColors.RESET);
    }
}
