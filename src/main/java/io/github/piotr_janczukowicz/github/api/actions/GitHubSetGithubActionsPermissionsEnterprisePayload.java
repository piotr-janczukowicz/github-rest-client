package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set GitHub Actions permissions for an enterprise.
 *
 * @param enabledOrganizations The policy that controls the organizations in the enterprise that are allowed to run
 *     GitHub Actions
 * @param allowedActions The permissions policy that controls the actions and reusable workflows that are allowed to run
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/set-github-actions-permissions-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSetGithubActionsPermissionsEnterprisePayload(
        GitHubEnabledOrganizations enabledOrganizations,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {

    public GitHubSetGithubActionsPermissionsEnterprisePayload {
        requireNonNull(enabledOrganizations, "enabledOrganizations");
    }
}
