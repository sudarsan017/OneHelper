package dev.sudarsan.onehelper.validation.common;

import java.util.Collection;
import java.util.Map;

public class ValueValidator {
    private ValueValidator() {
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
