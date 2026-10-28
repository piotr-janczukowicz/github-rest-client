package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Workflow runs of a repository.
 *
 * @param totalCount Total number of workflow runs
 * @param workflowRuns The workflow runs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"operations/actions/list-workflow-runs-for-repo/200", "operations/actions/list-workflow-runs/200"})
public record GitHubWorkflowRuns(int totalCount, List<GitHubWorkflowRun> workflowRuns) {

    public GitHubWorkflowRuns {
        requireNonNull(workflowRuns, "workflowRuns");
    }
}
