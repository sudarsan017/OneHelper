package dev.sudarsan.onehelper.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentStrategy {
    private final String prefix;
    private final String suffix;

    public CommentStrategy(@JsonProperty("prefix") String prefix,
                           @JsonProperty("suffix") String suffix) {
        this.prefix = prefix;
        this.suffix = suffix == null ? "" : suffix.trim();
    }

    public CommentStrategy(String prefix) {
        this.prefix = prefix;
        this.suffix = "";
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String comment(String line) {
        return prefix + line + suffix;
    }
}
