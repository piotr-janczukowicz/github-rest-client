package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to set default workflow permissions and pull request review approval setting.
 *
 * @param defaultWorkflowPermissions The default workflow permissions granted to the GITHUB_TOKEN
 * @param canApprovePullRequestReviews Whether GitHub Actions can approve pull requests
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-set-default-workflow-permissions")
public record GitHubActionsSetDefaultWorkflowPermissions(
        @Nullable GitHubActionsDefaultWorkflowPermissions defaultWorkflowPermissions,
        @Nullable GitHubActionsCanApprovePullRequestReviews canApprovePullRequestReviews) {}
