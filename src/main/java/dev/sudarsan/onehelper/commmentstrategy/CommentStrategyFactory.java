package dev.sudarsan.onehelper.commmentstrategy;

import dev.sudarsan.onehelper.exception.CommentStrategyException;

import java.util.Map;

public class CommentStrategyFactory {
    private CommentStrategyFactory() {
    }

    private static final Map<String, CommentStrategy> strategyMap = Map.of(
            "java", new PrefixSuffixCommentStrategy("//"),
            "js", new PrefixSuffixCommentStrategy("//"),
            "xml", new PrefixSuffixCommentStrategy("<!-- ", " -->"),
            "properties", new PrefixSuffixCommentStrategy("#")
    );

    public static CommentStrategy createCommentStrategy(String fileType) throws CommentStrategyException {
        if (!strategyMap.containsKey(fileType)) {
            throw new CommentStrategyException("Comment strategy unavailable for the file type: " + fileType);
        }
        return strategyMap.get(fileType.toLowerCase());
    }
}
