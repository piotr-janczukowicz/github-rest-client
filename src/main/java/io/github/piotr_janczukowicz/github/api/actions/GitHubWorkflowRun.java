package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.checks.GitHubSimpleCommit;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestMinimal;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * An invocation of a workflow.
 *
 * @param id The ID of the workflow run
 * @param name The name of the workflow run
 * @param nodeId The node ID of the workflow run
 * @param checkSuiteId The ID of the associated check suite
 * @param checkSuiteNodeId The node ID of the associated check suite
 * @param headBranch The head branch of the workflow run
 * @param headSha The SHA of the head commit that points to the version of the workflow being run
 * @param path The full path of the workflow
 * @param runNumber The auto incrementing run number for the workflow run
 * @param runAttempt Attempt number of the run, 1 for first attempt and higher if the workflow was re-run
 * @param referencedWorkflows Workflows referenced or reused by the workflow run
 * @param event The event that triggered the workflow run
 * @param status The status of the workflow run
 * @param conclusion The conclusion of the workflow run
 * @param workflowId The ID of the parent workflow
 * @param url The URL to the workflow run
 * @param htmlUrl The web URL of the workflow run
 * @param pullRequests Pull requests that are open with a head SHA or head branch that matches the workflow run
 * @param createdAt The creation time of the workflow run
 * @param updatedAt The last update time of the workflow run
 * @param actor The user that triggered the workflow run
 * @param triggeringActor The user that triggered the latest run
 * @param runStartedAt The start time of the latest run
 * @param jobsUrl The URL to the jobs for the workflow run
 * @param logsUrl The URL to download the logs for the workflow run
 * @param checkSuiteUrl The URL to the associated check suite
 * @param artifactsUrl The URL to the artifacts for the workflow run
 * @param cancelUrl The URL to cancel the workflow run
 * @param rerunUrl The URL to rerun the workflow run
 * @param previousAttemptUrl The URL to the previous attempted run of this workflow, if one exists
 * @param workflowUrl The URL to the workflow
 * @param headCommit The head commit of the workflow run
 * @param repository The repository of the workflow run
 * @param headRepository The head repository of the workflow run
 * @param headRepositoryId The ID of the head repository
 * @param displayTitle The event-specific title associated with the run or the run-name if set
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/workflow-run")
public record GitHubWorkflowRun(
        long id,
        @Nullable String name,
        String nodeId,
        @Nullable Long checkSuiteId,
        @Nullable String checkSuiteNodeId,
        @Nullable String headBranch,
        String headSha,
        String path,
        int runNumber,
        @Nullable Integer runAttempt,
        @Nullable List<GitHubReferencedWorkflow> referencedWorkflows,
        String event,
        @Nullable String status,
        @Nullable String conclusion,
        long workflowId,
        String url,
        String htmlUrl,
        @Nullable List<GitHubPullRequestMinimal> pullRequests,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubSimpleUser actor,
        @Nullable GitHubSimpleUser triggeringActor,
        @Nullable Instant runStartedAt,
        String jobsUrl,
        String logsUrl,
        String checkSuiteUrl,
        String artifactsUrl,
        String cancelUrl,
        String rerunUrl,
        @Nullable String previousAttemptUrl,
        String workflowUrl,
        @Nullable GitHubSimpleCommit headCommit,
        GitHubMinimalRepository repository,
        GitHubMinimalRepository headRepository,
        @Nullable Long headRepositoryId,
        String displayTitle) {

    public GitHubWorkflowRun {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(headSha, "headSha");
        requireNonNull(path, "path");
        requireNonNull(event, "event");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(jobsUrl, "jobsUrl");
        requireNonNull(logsUrl, "logsUrl");
        requireNonNull(checkSuiteUrl, "checkSuiteUrl");
        requireNonNull(artifactsUrl, "artifactsUrl");
        requireNonNull(cancelUrl, "cancelUrl");
        requireNonNull(rerunUrl, "rerunUrl");
        requireNonNull(workflowUrl, "workflowUrl");
        requireNonNull(repository, "repository");
        requireNonNull(headRepository, "headRepository");
        requireNonNull(displayTitle, "displayTitle");
    }
}
