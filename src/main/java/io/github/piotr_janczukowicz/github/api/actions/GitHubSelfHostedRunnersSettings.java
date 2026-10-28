package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Settings for self-hosted runners.
 *
 * @param enabledRepositories The policy that controls whether self-hosted runners can be used by repositories
 * @param selectedRepositoriesUrl The URL to manage selected repositories for self-hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/self-hosted-runners-settings")
public record GitHubSelfHostedRunnersSettings(
        GitHubSelfHostedRunnersEnabledRepositories enabledRepositories,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubSelfHostedRunnersSettings {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
