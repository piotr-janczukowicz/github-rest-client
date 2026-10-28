package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set GitHub Actions permissions for an organization.
 *
 * @param enabledRepositories The policy that controls the repositories in the organization that are allowed to run
 *     GitHub Actions
 * @param allowedActions The permissions policy that controls the actions and reusable workflows that are allowed to run
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-github-actions-permissions-organization/requestBody")
public record GitHubSetGithubActionsPermissionsOrganizationPayload(
        GitHubEnabledRepositories enabledRepositories,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {

    public GitHubSetGithubActionsPermissionsOrganizationPayload {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
