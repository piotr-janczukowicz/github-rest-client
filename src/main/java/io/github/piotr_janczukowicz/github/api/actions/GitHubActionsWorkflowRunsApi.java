package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeployment;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub Actions workflow runs REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsWorkflowRunsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_RUNS = PathTemplate.of("/repos/{owner}/{repo}/actions/runs");
    private static final PathTemplate REPO_RUN = PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}");
    private static final PathTemplate REPO_RUN_APPROVALS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/approvals");
    private static final PathTemplate REPO_RUN_APPROVE =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/approve");
    private static final PathTemplate REPO_RUN_ATTEMPT =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}");
    private static final PathTemplate REPO_RUN_ATTEMPT_LOGS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}/logs");
    private static final PathTemplate REPO_RUN_CANCEL =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/cancel");
    private static final PathTemplate REPO_RUN_CUSTOM_GATES =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/deployment_protection_rule");
    private static final PathTemplate REPO_RUN_FORCE_CANCEL =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/force-cancel");
    private static final PathTemplate REPO_RUN_LOGS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/logs");
    private static final PathTemplate REPO_RUN_PENDING_DEPLOYMENTS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/pending_deployments");
    private static final PathTemplate REPO_RUN_RERUN =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/rerun");
    private static final PathTemplate REPO_RUN_RERUN_FAILED_JOBS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/rerun-failed-jobs");
    private static final PathTemplate REPO_RUN_TIMING =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/timing");
    private static final PathTemplate REPO_JOB_RERUN =
            PathTemplate.of("/repos/{owner}/{repo}/actions/jobs/{job_id}/rerun");
    private static final PathTemplate REPO_WORKFLOW_RUNS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}/runs");

    private static final JsonType<List<GitHubEnvironmentApprovals>> APPROVALS_TYPE =
            JsonTypes.listOf(GitHubEnvironmentApprovals.class);
    private static final JsonType<List<GitHubPendingDeployment>> PENDING_DEPLOYMENTS_TYPE =
            JsonTypes.listOf(GitHubPendingDeployment.class);
    private static final JsonType<List<GitHubDeployment>> DEPLOYMENTS_TYPE = JsonTypes.listOf(GitHubDeployment.class);

    private static final GitHubListSpec<GitHubWorkflowRun> WORKFLOW_RUNS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubWorkflowRuns.class,
            GitHubWorkflowRuns::workflowRuns,
            GitHubWorkflowRuns::totalCount);

    /**
     * Workflow runs resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsWorkflowRunsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Delete a workflow run ({@code DELETE /repos/{owner}/{repo}/actions/runs/{run_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteWorkflowRun(final String owner, final String repo, final long runId) throws IOException {
        caller.send(delete(REPO_RUN, owner, repo, runId));
    }

    /**
     * Get a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return workflow run, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWorkflowRun> getWorkflowRun(final String owner, final String repo, final long runId)
            throws IOException {
        return getWorkflowRun(owner, repo, runId, GitHubGetWorkflowRunFilter.empty());
    }

    /**
     * Get a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param filter query filters
     * @return workflow run, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWorkflowRun> getWorkflowRun(
            final String owner, final String repo, final long runId, final GitHubGetWorkflowRunFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(REPO_RUN, owner, repo, runId).withQuery(filter.toQueryParameters()), GitHubWorkflowRun.class);
    }

    /**
     * Get the approvals of a workflow run waiting for environment review ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/approvals}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return environment approvals
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubEnvironmentApprovals> getReviewsForRun(final String owner, final String repo, final long runId)
            throws IOException {
        return caller.fetch(get(REPO_RUN_APPROVALS, owner, repo, runId), APPROVALS_TYPE);
    }

    /**
     * Approve a workflow run waiting for environment review ({@code POST
     * /repos/{owner}/{repo}/actions/runs/{run_id}/approve}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void approveWorkflowRun(final String owner, final String repo, final long runId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(post(REPO_RUN_APPROVE, owner, repo, runId));
    }

    /**
     * Get a run attempt ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param attemptNumber attempt number
     * @return workflow run attempt, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWorkflowRun> getWorkflowRunAttempt(
            final String owner, final String repo, final long runId, final long attemptNumber) throws IOException {
        return getWorkflowRunAttempt(owner, repo, runId, attemptNumber, GitHubGetWorkflowRunAttemptFilter.empty());
    }

    /**
     * Get a run attempt ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param attemptNumber attempt number
     * @param filter query filters
     * @return workflow run attempt, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWorkflowRun> getWorkflowRunAttempt(
            final String owner,
            final String repo,
            final long runId,
            final long attemptNumber,
            final GitHubGetWorkflowRunAttemptFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(REPO_RUN_ATTEMPT, owner, repo, runId, attemptNumber).withQuery(filter.toQueryParameters()),
                GitHubWorkflowRun.class);
    }

    /**
     * Download the logs of a run attempt ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}/logs}).
     *
     * <p>GitHub answers with a redirect to the log archive; this method returns that location.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param attemptNumber attempt number
     * @return redirect location of the logs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadWorkflowRunAttemptLogs(
            final String owner, final String repo, final long runId, final long attemptNumber) throws IOException {
        return caller.redirectLocation(get(REPO_RUN_ATTEMPT_LOGS, owner, repo, runId, attemptNumber));
    }

    /**
     * Cancel a workflow run ({@code POST /repos/{owner}/{repo}/actions/runs/{run_id}/cancel}).
     *
     * <p>GitHub answers HTTP 202 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void cancelWorkflowRun(final String owner, final String repo, final long runId) throws IOException {
        caller.send(post(REPO_RUN_CANCEL, owner, repo, runId));
    }

    /**
     * Review custom deployment protection rules for a run ({@code POST
     * /repos/{owner}/{repo}/actions/runs/{run_id}/deployment_protection_rule}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param payload approve or reject decision
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reviewCustomGatesForRun(
            final String owner, final String repo, final long runId, final GitHubReviewCustomGatesForRunPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(REPO_RUN_CUSTOM_GATES, owner, repo, runId), payload));
    }

    /**
     * Force-cancel a workflow run ({@code POST /repos/{owner}/{repo}/actions/runs/{run_id}/force-cancel}).
     *
     * <p>GitHub answers HTTP 202 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void forceCancelWorkflowRun(final String owner, final String repo, final long runId) throws IOException {
        caller.send(post(REPO_RUN_FORCE_CANCEL, owner, repo, runId));
    }

    /**
     * Download the logs of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/logs}).
     *
     * <p>GitHub answers with a redirect to the log archive; this method returns that location.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return redirect location of the logs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadWorkflowRunLogs(final String owner, final String repo, final long runId) throws IOException {
        return caller.redirectLocation(get(REPO_RUN_LOGS, owner, repo, runId));
    }

    /**
     * Delete the logs of a workflow run ({@code DELETE /repos/{owner}/{repo}/actions/runs/{run_id}/logs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteWorkflowRunLogs(final String owner, final String repo, final long runId) throws IOException {
        caller.send(delete(REPO_RUN_LOGS, owner, repo, runId));
    }

    /**
     * List deployments waiting for review in a run ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/pending_deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return pending deployments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubPendingDeployment> getPendingDeploymentsForRun(
            final String owner, final String repo, final long runId) throws IOException {
        return caller.fetch(get(REPO_RUN_PENDING_DEPLOYMENTS, owner, repo, runId), PENDING_DEPLOYMENTS_TYPE);
    }

    /**
     * Approve or reject pending deployments of a run ({@code POST
     * /repos/{owner}/{repo}/actions/runs/{run_id}/pending_deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param payload environments and decision
     * @return deployments of the reviewed environments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubDeployment> reviewPendingDeploymentsForRun(
            final String owner,
            final String repo,
            final long runId,
            final GitHubReviewPendingDeploymentsForRunPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_RUN_PENDING_DEPLOYMENTS, owner, repo, runId), payload), DEPLOYMENTS_TYPE);
    }

    /**
     * Re-run a workflow run ({@code POST /repos/{owner}/{repo}/actions/runs/{run_id}/rerun}) without options.
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunWorkflow(final String owner, final String repo, final long runId) throws IOException {
        reRunWorkflow(owner, repo, runId, null);
    }

    /**
     * Re-run a workflow run ({@code POST /repos/{owner}/{repo}/actions/runs/{run_id}/rerun}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param payload debug logging option, or {@code null} to send no body
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunWorkflow(
            final String owner, final String repo, final long runId, final @Nullable GitHubReRunWorkflowPayload payload)
            throws IOException {
        caller.send(caller.jsonIfPresent(post(REPO_RUN_RERUN, owner, repo, runId), payload));
    }

    /**
     * Re-run the failed jobs of a workflow run ({@code POST
     * /repos/{owner}/{repo}/actions/runs/{run_id}/rerun-failed-jobs}) without options.
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunWorkflowFailedJobs(final String owner, final String repo, final long runId) throws IOException {
        reRunWorkflowFailedJobs(owner, repo, runId, null);
    }

    /**
     * Re-run the failed jobs of a workflow run ({@code POST
     * /repos/{owner}/{repo}/actions/runs/{run_id}/rerun-failed-jobs}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param payload debug logging option, or {@code null} to send no body
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunWorkflowFailedJobs(
            final String owner,
            final String repo,
            final long runId,
            final @Nullable GitHubReRunWorkflowFailedJobsPayload payload)
            throws IOException {
        caller.send(caller.jsonIfPresent(post(REPO_RUN_RERUN_FAILED_JOBS, owner, repo, runId), payload));
    }

    /**
     * Get the billable time of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/timing}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return billable time per runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubWorkflowRunUsage> getWorkflowRunUsage(final String owner, final String repo, final long runId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(REPO_RUN_TIMING, owner, repo, runId), GitHubWorkflowRunUsage.class);
    }

    /**
     * List workflow runs of a repository ({@code GET /repos/{owner}/{repo}/actions/runs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRunsForRepo(final String owner, final String repo)
            throws IOException {
        return listWorkflowRunsForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List workflow runs of a repository ({@code GET /repos/{owner}/{repo}/actions/runs}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRunsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listWorkflowRunsForRepo(owner, repo, GitHubListWorkflowRunsForRepoFilter.empty(), request);
    }

    /**
     * List workflow runs of a repository ({@code GET /repos/{owner}/{repo}/actions/runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRunsForRepo(
            final String owner,
            final String repo,
            final GitHubListWorkflowRunsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUNS, filter.toQueryParameters(), request, WORKFLOW_RUNS_SPEC, owner, repo);
    }

    /**
     * Re-run a job of a workflow run ({@code POST /repos/{owner}/{repo}/actions/jobs/{job_id}/rerun}) without options.
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param jobId job ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunJobForWorkflowRun(final String owner, final String repo, final long jobId) throws IOException {
        reRunJobForWorkflowRun(owner, repo, jobId, null);
    }

    /**
     * Re-run a job of a workflow run ({@code POST /repos/{owner}/{repo}/actions/jobs/{job_id}/rerun}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param jobId job ID
     * @param payload debug options, or {@code null} to send no body
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reRunJobForWorkflowRun(
            final String owner,
            final String repo,
            final long jobId,
            final @Nullable GitHubReRunJobForWorkflowRunPayload payload)
            throws IOException {
        caller.send(caller.jsonIfPresent(post(REPO_JOB_RERUN, owner, repo, jobId), payload));
    }

    /**
     * List runs of a workflow ({@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}/runs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRuns(
            final String owner, final String repo, final String workflowId) throws IOException {
        return listWorkflowRuns(owner, repo, workflowId, GitHubPageRequest.byPage());
    }

    /**
     * List runs of a workflow ({@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}/runs}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @param request pagination token
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRuns(
            final String owner, final String repo, final String workflowId, final GitHubPageRequest request)
            throws IOException {
        return listWorkflowRuns(owner, repo, workflowId, GitHubListWorkflowRunsFilter.empty(), request);
    }

    /**
     * List runs of a workflow ({@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}/runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of workflow runs
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflowRun> listWorkflowRuns(
            final String owner,
            final String repo,
            final String workflowId,
            final GitHubListWorkflowRunsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_WORKFLOW_RUNS, filter.toQueryParameters(), request, WORKFLOW_RUNS_SPEC, owner, repo, workflowId);
    }
}
