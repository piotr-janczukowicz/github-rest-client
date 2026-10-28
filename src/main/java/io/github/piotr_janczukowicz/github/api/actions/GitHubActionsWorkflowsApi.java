package io.github.piotr_janczukowicz.github.api.actions;

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
import java.util.Map;
import java.util.Optional;

/** GitHub Actions workflows REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsWorkflowsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_WORKFLOWS = PathTemplate.of("/repos/{owner}/{repo}/actions/workflows");
    private static final PathTemplate REPO_WORKFLOW =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}");
    private static final PathTemplate REPO_WORKFLOW_DISABLE =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}/disable");
    private static final PathTemplate REPO_WORKFLOW_DISPATCHES =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}/dispatches");
    private static final PathTemplate REPO_WORKFLOW_ENABLE =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}/enable");
    private static final PathTemplate REPO_WORKFLOW_TIMING =
            PathTemplate.of("/repos/{owner}/{repo}/actions/workflows/{workflow_id}/timing");

    private static final GitHubListSpec<GitHubWorkflow> WORKFLOWS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubWorkflows.class,
            GitHubWorkflows::workflows,
            GitHubWorkflows::totalCount);

    /**
     * Workflows resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsWorkflowsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List workflows of a repository ({@code GET /repos/{owner}/{repo}/actions/workflows}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of workflows
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflow> listRepoWorkflows(final String owner, final String repo) throws IOException {
        return listRepoWorkflows(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List workflows of a repository ({@code GET /repos/{owner}/{repo}/actions/workflows}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of workflows
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubWorkflow> listRepoWorkflows(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_WORKFLOWS, Map.of(), request, WORKFLOWS_SPEC, owner, repo);
    }

    /**
     * Get a workflow ({@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @return workflow, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWorkflow> getWorkflow(final String owner, final String repo, final String workflowId)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_WORKFLOW, owner, repo, workflowId), GitHubWorkflow.class);
    }

    /**
     * Disable a workflow ({@code PUT /repos/{owner}/{repo}/actions/workflows/{workflow_id}/disable}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableWorkflow(final String owner, final String repo, final String workflowId) throws IOException {
        caller.send(put(REPO_WORKFLOW_DISABLE, owner, repo, workflowId));
    }

    /**
     * Create a workflow dispatch event ({@code POST /repos/{owner}/{repo}/actions/workflows/{workflow_id}/dispatches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @param payload git reference and inputs
     * @return run details of the dispatched workflow
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubWorkflowDispatchResponse createWorkflowDispatch(
            final String owner,
            final String repo,
            final String workflowId,
            final GitHubCreateWorkflowDispatchPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_WORKFLOW_DISPATCHES, owner, repo, workflowId), payload),
                GitHubWorkflowDispatchResponse.class);
    }

    /**
     * Enable a workflow ({@code PUT /repos/{owner}/{repo}/actions/workflows/{workflow_id}/enable}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableWorkflow(final String owner, final String repo, final String workflowId) throws IOException {
        caller.send(put(REPO_WORKFLOW_ENABLE, owner, repo, workflowId));
    }

    /**
     * Get the billable time of a workflow ({@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}/timing}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param workflowId workflow ID or file name
     * @return billable time per runner, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubWorkflowUsage> getWorkflowUsage(
            final String owner, final String repo, final String workflowId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(REPO_WORKFLOW_TIMING, owner, repo, workflowId), GitHubWorkflowUsage.class);
    }
}
