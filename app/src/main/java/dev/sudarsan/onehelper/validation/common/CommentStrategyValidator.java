package dev.sudarsan.onehelper.validation.common;

import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.strategy.CommentStrategy;

import java.util.Map;

public class CommentStrategyValidator {
    private CommentStrategyValidator() {
    }

    public static void validate(Map<String, CommentStrategy> commentStrategyMap) throws ValidationException {
        for (Map.Entry<String, CommentStrategy> entry : commentStrategyMap.entrySet()) {
            validateEntry(entry);
        }
    }

    private static void validateEntry(Map.Entry<String, CommentStrategy> entry) throws ValidationException {
        CommentStrategy commentStrategy = entry.getValue();

        if (commentStrategy == null) {
            throw new ValidationException("There is no comment Strategy given for the file type: " + entry.getKey());
        }

        if (ValueValidator.isNullOrEmpty(commentStrategy.getPrefix())) {
            throw new ValidationException("Prefix cannot be null for the comment strategy");
        }
    }
}
