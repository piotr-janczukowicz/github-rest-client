package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Jobs of a workflow run or workflow run attempt.
 *
 * @param totalCount Total number of jobs
 * @param jobs The jobs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-jobs-for-workflow-run/200",
    "operations/actions/list-jobs-for-workflow-run-attempt/200"
})
public record GitHubJobs(int totalCount, List<GitHubJob> jobs) {

    public GitHubJobs {
        requireNonNull(jobs, "jobs");
    }
}
