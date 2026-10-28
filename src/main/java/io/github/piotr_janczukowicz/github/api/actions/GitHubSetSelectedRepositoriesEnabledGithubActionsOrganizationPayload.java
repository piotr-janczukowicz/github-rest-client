package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories allowed to run GitHub Actions in an organization.
 *
 * @param selectedRepositoryIds List of repository IDs to enable for GitHub Actions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-selected-repositories-enabled-github-actions-organization/requestBody")
public record GitHubSetSelectedRepositoriesEnabledGithubActionsOrganizationPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedRepositoriesEnabledGithubActionsOrganizationPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
