package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions permissions of an organization.
 *
 * @param enabledRepositories The policy that controls the repositories allowed to run GitHub Actions
 * @param selectedRepositoriesUrl The API URL to get or set the selected repositories
 * @param allowedActions The permissions policy that controls the actions and reusable workflows allowed to run
 * @param selectedActionsUrl The API URL to get or set the selected actions and reusable workflows
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-organization-permissions")
public record GitHubActionsOrganizationPermissions(
        GitHubEnabledRepositories enabledRepositories,
        @Nullable String selectedRepositoriesUrl,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubSelectedActionsUrl selectedActionsUrl,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {

    public GitHubActionsOrganizationPermissions {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
