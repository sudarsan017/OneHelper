package dev.sudarsan.onehelper.modificationconfig;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.modification.Modification;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "modificationType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LineBasedModificationConfig.class, name = "LINE"),
        @JsonSubTypes.Type(value = WholeFileModificationConfig.class, name = "WHOLE")
})
public interface ModificationConfig {
    Modification toModification() throws ModificationException;
}
