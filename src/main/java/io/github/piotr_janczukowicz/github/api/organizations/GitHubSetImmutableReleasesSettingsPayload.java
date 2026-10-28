package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set immutable releases settings for an organization.
 *
 * @param enforcedRepositories The policy that controls how immutable releases are enforced in the organization
 * @param selectedRepositoryIds Selected repository ids
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-immutable-releases-settings/requestBody")
public record GitHubSetImmutableReleasesSettingsPayload(
        GitHubImmutableReleasesEnforcedRepositories enforcedRepositories,
        @Nullable List<Long> selectedRepositoryIds) {

    public GitHubSetImmutableReleasesSettingsPayload {
        requireNonNull(enforcedRepositories, "enforcedRepositories");
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }
}
