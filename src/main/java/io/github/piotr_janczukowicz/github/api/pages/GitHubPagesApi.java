package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
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

/** GitHub repository Pages REST resource for API version {@code 2026-03-10}. */
public final class GitHubPagesApi extends GitHubResourceApi {

    private static final PathTemplate PAGES_DEPLOYMENT_CANCEL =
            PathTemplate.of("/repos/{owner}/{repo}/pages/deployments/{pages_deployment_id}/cancel");
    private static final PathTemplate PAGES_DEPLOYMENT =
            PathTemplate.of("/repos/{owner}/{repo}/pages/deployments/{pages_deployment_id}");
    private static final PathTemplate PAGES_DEPLOYMENTS = PathTemplate.of("/repos/{owner}/{repo}/pages/deployments");
    private static final PathTemplate PAGES_HEALTH = PathTemplate.of("/repos/{owner}/{repo}/pages/health");
    private static final PathTemplate PAGES_BUILDS = PathTemplate.of("/repos/{owner}/{repo}/pages/builds");
    private static final PathTemplate PAGES_BUILDS_LATEST =
            PathTemplate.of("/repos/{owner}/{repo}/pages/builds/latest");
    private static final PathTemplate PAGES_BUILD = PathTemplate.of("/repos/{owner}/{repo}/pages/builds/{build_id}");
    private static final PathTemplate PAGES = PathTemplate.of("/repos/{owner}/{repo}/pages");

    private static final JsonType<List<GitHubPageBuild>> PAGE_BUILDS_TYPE = JsonTypes.listOf(GitHubPageBuild.class);

    /**
     * Pages resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPagesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get a GitHub Pages site ({@code GET /repos/{owner}/{repo}/pages}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return pages site, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPagesSite> getPages(final String owner, final String repo) throws IOException {
        return caller.fetchOrEmpty(get(PAGES, owner, repo), GitHubPagesSite.class);
    }

    /**
     * Create a GitHub Pages site ({@code POST /repos/{owner}/{repo}/pages}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload site configuration; the spec requires a body with a {@code source} or a {@code build_type}
     * @return created pages site
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPagesSite createPagesSite(
            final String owner, final String repo, final GitHubCreatePagesSitePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(PAGES, owner, repo), payload), GitHubPagesSite.class);
    }

    /**
     * Update a GitHub Pages site ({@code PUT /repos/{owner}/{repo}/pages}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload site updates
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateInformationAboutPagesSite(
            final String owner, final String repo, final GitHubUpdatePagesSitePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(PAGES, owner, repo), payload));
    }

    /**
     * Delete a GitHub Pages site ({@code DELETE /repos/{owner}/{repo}/pages}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePagesSite(final String owner, final String repo) throws IOException {
        caller.send(delete(PAGES, owner, repo));
    }

    /**
     * List Pages builds ({@code GET /repos/{owner}/{repo}/pages/builds}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of page builds
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPageBuild> listPagesBuilds(final String owner, final String repo) throws IOException {
        return listPagesBuilds(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List Pages builds ({@code GET /repos/{owner}/{repo}/pages/builds}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of page builds
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPageBuild> listPagesBuilds(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PAGES_BUILDS, Map.of(), request, PAGE_BUILDS_TYPE, owner, repo);
    }

    /**
     * Get a Pages build ({@code GET /repos/{owner}/{repo}/pages/builds/{build_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param buildId build id
     * @return page build, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubPageBuild> getPagesBuild(final String owner, final String repo, final long buildId)
            throws IOException {

        return caller.fetchOrEmpty(get(PAGES_BUILD, owner, repo, buildId), GitHubPageBuild.class);
    }

    /**
     * Get the latest Pages build ({@code GET /repos/{owner}/{repo}/pages/builds/latest}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return latest page build
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPageBuild getLatestPagesBuild(final String owner, final String repo) throws IOException {
        return caller.fetch(get(PAGES_BUILDS_LATEST, owner, repo), GitHubPageBuild.class);
    }

    /**
     * Request a Pages build ({@code POST /repos/{owner}/{repo}/pages/builds}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return build status
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPageBuildStatus requestPagesBuild(final String owner, final String repo) throws IOException {
        return caller.fetch(post(PAGES_BUILDS, owner, repo), GitHubPageBuildStatus.class);
    }

    /**
     * Get a Pages health check ({@code GET /repos/{owner}/{repo}/pages/health}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return health check result, or empty on HTTP 202 (the check is still running, try again later) or no body or
     *     {@code {}}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful; for example 404 (no Pages site), 401, 403 and 5xx are
     *     thrown
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubPagesHealthCheck> getPagesHealthCheck(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchUnless(get(PAGES_HEALTH, owner, repo), GitHubPagesHealthCheck.class, HttpStatus.ACCEPTED);
    }

    /**
     * Create a Pages deployment ({@code POST /repos/{owner}/{repo}/pages/deployments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload deployment request
     * @return pages deployment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPagesDeployment createPagesDeployment(
            final String owner, final String repo, final GitHubCreatePagesDeploymentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(PAGES_DEPLOYMENTS, owner, repo), payload), GitHubPagesDeployment.class);
    }

    /**
     * Get a Pages deployment ({@code GET /repos/{owner}/{repo}/pages/deployments/{pages_deployment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pagesDeploymentId pages deployment id or commit SHA
     * @return deployment status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code pagesDeploymentId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPagesDeploymentStatusResponse> getPagesDeployment(
            final String owner, final String repo, final String pagesDeploymentId) throws IOException {

        return caller.fetchOrEmpty(
                get(PAGES_DEPLOYMENT, owner, repo, pagesDeploymentId), GitHubPagesDeploymentStatusResponse.class);
    }

    /**
     * Cancel a Pages deployment ({@code POST /repos/{owner}/{repo}/pages/deployments/{pages_deployment_id}/cancel}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pagesDeploymentId pages deployment id or commit SHA
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code pagesDeploymentId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void cancelPagesDeployment(final String owner, final String repo, final String pagesDeploymentId)
            throws IOException {

        caller.send(post(PAGES_DEPLOYMENT_CANCEL, owner, repo, pagesDeploymentId));
    }
}
