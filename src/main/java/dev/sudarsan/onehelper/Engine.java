package dev.sudarsan.onehelper;

import dev.sudarsan.onehelper.dto.RootConfig;
import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.entities.UserSelection;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.modificationconfig.ModificationConfig;
import dev.sudarsan.onehelper.util.Console;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

import java.util.List;

public class Engine {
    private final RootConfig rootConfig;

    public Engine(RootConfig rootConfig) {
        this.rootConfig = rootConfig;
    }

    public void run(UserSelection selection, ProjectContext context) throws ModificationException {
        List<ModificationConfig> modificationConfigs = rootConfig.getProjects().get(selection.getProject()).getSetups().get(selection.getSetup());

        if (ValueCheckerUtil.isNullOrEmpty(modificationConfigs)) {
            Console.error("No modifications to apply");
            return;
        }

        for (ModificationConfig modificationConfig : modificationConfigs) {
            modificationConfig.toModification().apply(context);
        }

        Console.success("Modifications have been applied successfully");
    }
}
