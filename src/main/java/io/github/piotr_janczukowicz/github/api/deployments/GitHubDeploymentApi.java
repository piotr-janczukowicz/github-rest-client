package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub deployments REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeploymentApi extends GitHubResourceApi {

    private static final PathTemplate DEPLOYMENT = PathTemplate.of("/repos/{owner}/{repo}/deployments/{deployment_id}");
    private static final PathTemplate DEPLOYMENTS = PathTemplate.of("/repos/{owner}/{repo}/deployments");

    private static final JsonType<List<GitHubDeployment>> DEPLOYMENTS_TYPE = JsonTypes.listOf(GitHubDeployment.class);

    /**
     * Deployments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List deployments ({@code GET /repos/{owner}/{repo}/deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of deployments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeployment> listDeployments(final String owner, final String repo) throws IOException {
        return listDeployments(owner, repo, GitHubListDeploymentsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List deployments ({@code GET /repos/{owner}/{repo}/deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of deployments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeployment> listDeployments(
            final String owner,
            final String repo,
            final GitHubListDeploymentsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(DEPLOYMENTS, filter.toQueryParameters(), request, DEPLOYMENTS_TYPE, owner, repo);
    }

    /**
     * Get a deployment ({@code GET /repos/{owner}/{repo}/deployments/{deployment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @return deployment, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDeployment> getDeployment(final String owner, final String repo, final long deploymentId)
            throws IOException {

        return caller.fetchOrEmpty(get(DEPLOYMENT, owner, repo, deploymentId), GitHubDeployment.class);
    }

    /**
     * Create a deployment ({@code POST /repos/{owner}/{repo}/deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload deployment to create
     * @return created deployment (HTTP 201) or merged-branch acceptance (HTTP 202)
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCreateDeploymentResult createDeployment(
            final String owner, final String repo, final GitHubCreateDeploymentPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchByStatus(
                caller.json(post(DEPLOYMENTS, owner, repo), payload),
                body -> GitHubCreateDeploymentResult.ofCreated(body.as(GitHubDeployment.class)),
                Map.of(
                        HttpStatus.ACCEPTED,
                        body -> GitHubCreateDeploymentResult.ofMergedBranch(
                                body.as(GitHubDeploymentMergedBranch.class))));
    }

    /**
     * Delete a deployment ({@code DELETE /repos/{owner}/{repo}/deployments/{deployment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param deploymentId deployment id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteDeployment(final String owner, final String repo, final long deploymentId) throws IOException {
        caller.send(delete(DEPLOYMENT, owner, repo, deploymentId));
    }
}
