package dev.sudarsan.onehelper.util;

public class Console {
    public static void success(String message) {
        System.out.println(ConsoleColors.GREEN + message + ConsoleColors.RESET);
    }

    public static void error(String message) {
        System.out.println(ConsoleColors.RED + message + ConsoleColors.RESET);
    }

    public static void error(String message, Exception e) {
        System.out.println(ConsoleColors.RED + message + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
    }

    public static void info(String message) {
        System.out.println(ConsoleColors.CYAN + message + ConsoleColors.RESET);
    }
}
