package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set GitHub Actions permissions for a repository.
 *
 * @param enabled Whether GitHub Actions is enabled on the repository
 * @param allowedActions The permissions policy that controls the actions and reusable workflows that are allowed to run
 * @param shaPinningRequired Whether actions must be pinned to a full-length commit SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-github-actions-permissions-repository/requestBody")
public record GitHubSetGithubActionsPermissionsRepositoryPayload(
        boolean enabled,
        @Nullable GitHubAllowedActions allowedActions,
        @Nullable GitHubShaPinningRequired shaPinningRequired) {}
