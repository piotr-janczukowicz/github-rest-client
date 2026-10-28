package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions permissions of an enterprise.
 *
 * @param enabledOrganizations The policy that controls the organizations in the enterprise that are allowed to run
 *     GitHub Actions
 * @param selectedOrganizationsUrl The API URL to use to get or set the selected organizations that are allowed to run
 *     GitHub Actions, when {@code enabled_organizations} is set to {@code selected}
 * @param allowedActions The permissions policy that controls the actions and reusable workflows allowed to run
 * @param selectedActionsUrl The API URL to get or set the selected actions and reusable workflows
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-enterprise-permissions")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubActionsEnterprisePermissions(
        GitHubEnabledOrganizations enabledOrganizations,
        @Nullable String selectedOrganizationsUrl,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubSelectedActionsUrl selectedActionsUrl,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {

    public GitHubActionsEnterprisePermissions {
        requireNonNull(enabledOrganizations, "enabledOrganizations");
    }
}
