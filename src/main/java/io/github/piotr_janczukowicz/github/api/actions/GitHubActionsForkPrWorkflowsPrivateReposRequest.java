package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to update fork pull request workflow settings for private repositories.
 *
 * @param runWorkflowsFromForkPullRequests Whether workflows triggered by pull requests from forks are allowed to run
 * @param sendWriteTokensToWorkflows Whether GitHub Actions can create pull requests or submit approving reviews
 * @param sendSecretsAndVariables Whether to make secrets and variables available to fork pull request workflows
 * @param requireApprovalForForkPrWorkflows Whether fork pull request workflows require approval from a maintainer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-fork-pr-workflows-private-repos-request")
public record GitHubActionsForkPrWorkflowsPrivateReposRequest(
        boolean runWorkflowsFromForkPullRequests,
        @Nullable Boolean sendWriteTokensToWorkflows,
        @Nullable Boolean sendSecretsAndVariables,
        @Nullable Boolean requireApprovalForForkPrWorkflows) {}
