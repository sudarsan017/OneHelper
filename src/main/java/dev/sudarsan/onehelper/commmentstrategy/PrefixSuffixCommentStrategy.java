package dev.sudarsan.onehelper.commmentstrategy;

public class PrefixSuffixCommentStrategy implements CommentStrategy {
    private final String prefix;
    private final String suffix;

    public PrefixSuffixCommentStrategy(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public PrefixSuffixCommentStrategy(String prefix) {
        this.prefix = prefix;
        this.suffix = "";
    }

    @Override
    public String comment(String line) {
        return prefix + line + suffix;
    }
}
