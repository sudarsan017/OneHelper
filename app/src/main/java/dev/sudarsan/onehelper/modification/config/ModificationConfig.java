package dev.sudarsan.onehelper.modification.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "modificationType"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = LineBasedModificationConfig.class, name = "LINE"),
        @JsonSubTypes.Type(value = WholeFileModificationConfig.class, name = "WHOLE"),
        @JsonSubTypes.Type(value = IntellijIdeConfigModificationConfig.class, name = "INTELLIJ_IDE_CONFIG"),
        @JsonSubTypes.Type(value = GitPatchModificationConfig.class, name = "GIT_PATCH")
})
public interface ModificationConfig {
    Boolean isEnabled();
}
