package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Default workflow permissions and pull request review approval setting.
 *
 * @param defaultWorkflowPermissions The default workflow permissions granted to the GITHUB_TOKEN
 * @param canApprovePullRequestReviews Whether GitHub Actions can approve pull requests
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-get-default-workflow-permissions")
public record GitHubActionsGetDefaultWorkflowPermissions(
        GitHubActionsDefaultWorkflowPermissions defaultWorkflowPermissions,
        GitHubActionsCanApprovePullRequestReviews canApprovePullRequestReviews) {

    public GitHubActionsGetDefaultWorkflowPermissions {
        requireNonNull(defaultWorkflowPermissions, "defaultWorkflowPermissions");
        requireNonNull(canApprovePullRequestReviews, "canApprovePullRequestReviews");
    }
}
