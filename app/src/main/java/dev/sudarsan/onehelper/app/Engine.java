package dev.sudarsan.onehelper.app;

import dev.sudarsan.onehelper.config.RuntimeConfig;
import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.io.Console;
import dev.sudarsan.onehelper.modification.config.ModificationConfig;
import dev.sudarsan.onehelper.modification.factory.ModificationFactory;
import dev.sudarsan.onehelper.modification.runtime.Modification;

import java.util.List;

public class Engine {
    private final RuntimeConfig runtimeConfig;

    public Engine(RuntimeConfig runtimeConfig) {
        this.runtimeConfig = runtimeConfig;
    }

    public void run(UserSelection selection, ProjectContext context) throws ModificationException {
        List<ModificationConfig> modificationConfigs = runtimeConfig.getRootConfig().getProjects().
                get(selection.getProject()).getSetups().get(selection.getSetup());

        if (modificationConfigs.isEmpty()) {
            throw new ModificationException("No modifications found for project " + selection.getProject());
        }

        ModificationFactory factory = new ModificationFactory(runtimeConfig.getCommentStrategyMap(), runtimeConfig.getResolutionInput());

        List<Modification> modifications = factory.getModifications(modificationConfigs);

        for (Modification modification : modifications) {
            modification.apply(context);
        }

        Console.success("Modifications have been applied successfully");
    }
}
