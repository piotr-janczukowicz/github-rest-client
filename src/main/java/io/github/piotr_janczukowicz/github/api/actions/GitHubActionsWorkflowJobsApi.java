package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeast;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

/** GitHub Actions workflow jobs REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsWorkflowJobsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_JOB = PathTemplate.of("/repos/{owner}/{repo}/actions/jobs/{job_id}");
    private static final PathTemplate REPO_JOB_LOGS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/jobs/{job_id}/logs");
    private static final PathTemplate REPO_JOB_STEP_LOGS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/jobs/{job_id}/steps/{step_number}/logs");
    private static final PathTemplate REPO_RUN_ATTEMPT_JOBS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}/jobs");
    private static final PathTemplate REPO_RUN_JOBS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/jobs");

    private static final GitHubListSpec<GitHubJob> JOBS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE, GitHubJobs.class, GitHubJobs::jobs, GitHubJobs::totalCount);

    /**
     * Workflow jobs resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsWorkflowJobsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get a job for a workflow run ({@code GET /repos/{owner}/{repo}/actions/jobs/{job_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param jobId job ID
     * @return job, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubJob> getJobForWorkflowRun(final String owner, final String repo, final long jobId)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_JOB, owner, repo, jobId), GitHubJob.class);
    }

    /**
     * Download the logs of a job ({@code GET /repos/{owner}/{repo}/actions/jobs/{job_id}/logs}).
     *
     * <p>GitHub answers with a redirect to the log archive; this method returns that location.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param jobId job ID
     * @return redirect location of the logs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadJobLogsForWorkflowRun(final String owner, final String repo, final long jobId)
            throws IOException {
        return caller.redirectLocation(get(REPO_JOB_LOGS, owner, repo, jobId));
    }

    /**
     * Download the logs of one step of a job ({@code GET
     * /repos/{owner}/{repo}/actions/jobs/{job_id}/steps/{step_number}/logs}).
     *
     * <p>GitHub answers with a redirect to a plain text file of the step logs that expires after one minute; this
     * method returns that location. To download the complete job logs use {@link #downloadJobLogsForWorkflowRun(String,
     * String, long)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param jobId job ID
     * @param stepNumber zero-based position number of the step in the job
     * @return redirect location of the step logs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code stepNumber} is negative
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public URI downloadStepLogsForWorkflowRunJob(
            final String owner, final String repo, final long jobId, final int stepNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireAtLeast(stepNumber, 0, "stepNumber");

        return caller.redirectLocation(get(REPO_JOB_STEP_LOGS, owner, repo, jobId, stepNumber));
    }

    /**
     * List jobs of a run attempt ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}/jobs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param attemptNumber attempt number
     * @return first HTTP page of jobs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubJob> listJobsForWorkflowRunAttempt(
            final String owner, final String repo, final long runId, final long attemptNumber) throws IOException {
        return listJobsForWorkflowRunAttempt(owner, repo, runId, attemptNumber, GitHubPageRequest.byPage());
    }

    /**
     * List jobs of a run attempt ({@code GET
     * /repos/{owner}/{repo}/actions/runs/{run_id}/attempts/{attempt_number}/jobs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param attemptNumber attempt number
     * @param request pagination token
     * @return first HTTP page of jobs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubJob> listJobsForWorkflowRunAttempt(
            final String owner,
            final String repo,
            final long runId,
            final long attemptNumber,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUN_ATTEMPT_JOBS, Map.of(), request, JOBS_SPEC, owner, repo, runId, attemptNumber);
    }

    /**
     * List jobs of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/jobs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return first HTTP page of jobs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubJob> listJobsForWorkflowRun(final String owner, final String repo, final long runId)
            throws IOException {
        return listJobsForWorkflowRun(owner, repo, runId, GitHubPageRequest.byPage());
    }

    /**
     * List jobs of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/jobs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param request pagination token
     * @return first HTTP page of jobs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubJob> listJobsForWorkflowRun(
            final String owner, final String repo, final long runId, final GitHubPageRequest request)
            throws IOException {
        return listJobsForWorkflowRun(owner, repo, runId, GitHubListJobsForWorkflowRunFilter.empty(), request);
    }

    /**
     * List jobs of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/jobs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of jobs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubJob> listJobsForWorkflowRun(
            final String owner,
            final String repo,
            final long runId,
            final GitHubListJobsForWorkflowRunFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUN_JOBS, filter.toQueryParameters(), request, JOBS_SPEC, owner, repo, runId);
    }
}
