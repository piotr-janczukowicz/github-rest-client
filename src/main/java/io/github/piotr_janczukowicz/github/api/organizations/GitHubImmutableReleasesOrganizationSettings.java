package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Check immutable releases organization settings.
 *
 * @param enforcedRepositories The policy that controls how immutable releases are enforced in the organization
 * @param selectedRepositoriesUrl The API URL to use to get or set the selected repositories for immutable releases
 *     enforcement
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/immutable-releases-organization-settings")
@RecordBuilder
public record GitHubImmutableReleasesOrganizationSettings(
        GitHubImmutableReleasesEnforcedRepositories enforcedRepositories,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubImmutableReleasesOrganizationSettings {
        requireNonNull(enforcedRepositories, "enforcedRepositories");
    }
}
