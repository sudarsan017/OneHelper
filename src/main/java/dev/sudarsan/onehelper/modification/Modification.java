package dev.sudarsan.onehelper.modification;

import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

public interface Modification {
    void apply(ProjectContext context) throws ModificationException;
}
