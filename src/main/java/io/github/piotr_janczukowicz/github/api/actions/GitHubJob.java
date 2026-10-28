package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Information of a job execution in a workflow run.
 *
 * @param id The id of the job
 * @param runId The id of the associated workflow run
 * @param runUrl The API URL of the associated workflow run
 * @param runAttempt Attempt number of the associated workflow run
 * @param nodeId The node id of the job
 * @param headSha The SHA of the commit that is being run
 * @param url The API URL of the job
 * @param htmlUrl The web URL of the job
 * @param status The phase of the lifecycle that the job is currently in
 * @param conclusion The outcome of the job
 * @param createdAt The time that the job was created
 * @param startedAt The time that the job started
 * @param completedAt The time that the job finished
 * @param name The name of the job
 * @param steps Steps in this job
 * @param checkRunUrl The API URL of the check run of the job
 * @param labels Labels for the workflow job
 * @param runnerId The ID of the runner to which this job has been assigned
 * @param runnerName The name of the runner to which this job has been assigned
 * @param runnerGroupId The ID of the runner group to which this job has been assigned
 * @param runnerGroupName The name of the runner group to which this job has been assigned
 * @param workflowName The name of the workflow
 * @param headBranch The name of the current branch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/job")
public record GitHubJob(
        long id,
        long runId,
        String runUrl,
        @Nullable Integer runAttempt,
        String nodeId,
        String headSha,
        String url,
        @Nullable String htmlUrl,
        GitHubJobStatus status,
        @Nullable GitHubJobConclusion conclusion,
        Instant createdAt,
        Instant startedAt,
        @Nullable Instant completedAt,
        String name,
        @Nullable List<GitHubJobStep> steps,
        String checkRunUrl,
        List<String> labels,
        @Nullable Long runnerId,
        @Nullable String runnerName,
        @Nullable Long runnerGroupId,
        @Nullable String runnerGroupName,
        @Nullable String workflowName,
        @Nullable String headBranch) {

    public GitHubJob {
        requireNonNull(runUrl, "runUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(headSha, "headSha");
        requireNonNull(url, "url");
        requireNonNull(status, "status");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(startedAt, "startedAt");
        requireNonNull(name, "name");
        requireNonNull(checkRunUrl, "checkRunUrl");
        requireNonNull(labels, "labels");
    }
}
