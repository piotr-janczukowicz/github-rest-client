package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Response containing the workflow run ID and URLs.
 *
 * @param workflowRunId The ID of the workflow run
 * @param runUrl The URL to the workflow run
 * @param htmlUrl The web URL of the workflow run
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/workflow-dispatch-response")
public record GitHubWorkflowDispatchResponse(GitHubWorkflowRunId workflowRunId, String runUrl, String htmlUrl) {

    public GitHubWorkflowDispatchResponse {
        requireNonNull(workflowRunId, "workflowRunId");
        requireNonNull(runUrl, "runUrl");
        requireNonNull(htmlUrl, "htmlUrl");
    }
}
