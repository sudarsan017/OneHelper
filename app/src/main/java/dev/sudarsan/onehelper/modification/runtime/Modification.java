package dev.sudarsan.onehelper.modification.runtime;

import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

public interface Modification {
    void apply(ProjectContext context) throws ModificationException;
}
