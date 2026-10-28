package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub deployment statuses REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeploymentsStatusesApi extends GitHubResourceApi {

    private static final PathTemplate DEPLOYMENT_STATUSES =
            PathTemplate.of("/repos/{owner}/{repo}/deployments/{deployment_id}/statuses");
    private static final PathTemplate DEPLOYMENT_STATUS =
            PathTemplate.of("/repos/{owner}/{repo}/deployments/{deployment_id}/statuses/{status_id}");

    private static final JsonType<List<GitHubDeploymentStatus>> DEPLOYMENT_STATUSES_TYPE =
            JsonTypes.listOf(GitHubDeploymentStatus.class);

    /**
     * Deployment statuses resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentsStatusesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List deployment statuses ({@code GET /repos/{owner}/{repo}/deployments/{deployment_id}/statuses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @return first HTTP page of deployment statuses
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeploymentStatus> listDeploymentStatuses(
            final String owner, final String repo, final long deploymentId) throws IOException {
        return listDeploymentStatuses(owner, repo, deploymentId, GitHubPageRequest.byPage());
    }

    /**
     * List deployment statuses ({@code GET /repos/{owner}/{repo}/deployments/{deployment_id}/statuses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @param request pagination token
     * @return first HTTP page of deployment statuses
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeploymentStatus> listDeploymentStatuses(
            final String owner, final String repo, final long deploymentId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(DEPLOYMENT_STATUSES, Map.of(), request, DEPLOYMENT_STATUSES_TYPE, owner, repo, deploymentId);
    }

    /**
     * Get a deployment status ({@code GET /repos/{owner}/{repo}/deployments/{deployment_id}/statuses/{status_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @param statusId status id
     * @return deployment status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDeploymentStatus> getDeploymentStatus(
            final String owner, final String repo, final long deploymentId, final long statusId) throws IOException {

        return caller.fetchOrEmpty(
                get(DEPLOYMENT_STATUS, owner, repo, deploymentId, statusId), GitHubDeploymentStatus.class);
    }

    /**
     * Create a deployment status ({@code POST /repos/{owner}/{repo}/deployments/{deployment_id}/statuses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @param payload status to create
     * @return created deployment status
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDeploymentStatus createDeploymentStatus(
            final String owner,
            final String repo,
            final long deploymentId,
            final GitHubCreateDeploymentStatusPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(DEPLOYMENT_STATUSES, owner, repo, deploymentId), payload),
                GitHubDeploymentStatus.class);
    }
}
