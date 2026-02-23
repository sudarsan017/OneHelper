package dev.sudarsan.onehelper.util;

import java.util.Collection;
import java.util.Map;

public class ValueCheckerUtil {
    private ValueCheckerUtil() {
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
