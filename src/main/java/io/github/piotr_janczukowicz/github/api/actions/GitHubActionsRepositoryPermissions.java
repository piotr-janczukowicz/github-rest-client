package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Actions permissions of a repository.
 *
 * @param enabled Whether GitHub Actions is enabled on the repository
 * @param allowedActions The permissions policy that controls the actions and reusable workflows allowed to run
 * @param selectedActionsUrl The API URL to get or set the selected actions and reusable workflows
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-repository-permissions")
public record GitHubActionsRepositoryPermissions(
        GitHubActionsEnabled enabled,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubSelectedActionsUrl selectedActionsUrl,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {

    public GitHubActionsRepositoryPermissions {
        requireNonNull(enabled, "enabled");
    }
}
