package io.github.piotr_janczukowicz.github.rest.json;

import io.github.piotr_janczukowicz.github.json.GitHubJsonModule;
import tools.jackson.databind.JacksonModule;

/** Registers the type adapters and mixins of the generated REST models with the default GitHub JSON codec. */
public final class GitHubRestJsonModule implements GitHubJsonModule {

    private final JacksonModule module;

    /**
     * Creates the module with the adapters and mixins of the REST models; the {@link java.util.ServiceLoader} calls it.
     */
    public GitHubRestJsonModule() {
        module = GitHubApiModules.create();
    }

    @Override
    public JacksonModule module() {
        return module;
    }
}
