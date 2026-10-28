package io.github.piotr_janczukowicz.github.api.codespaces;

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

/** GitHub Codespaces REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespaceApi extends GitHubResourceApi {

    private static final PathTemplate REPO_CODESPACES = PathTemplate.of("/repos/{owner}/{repo}/codespaces");
    private static final PathTemplate REPO_CODESPACES_DEVCONTAINERS =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/devcontainers");
    private static final PathTemplate REPO_CODESPACES_NEW = PathTemplate.of("/repos/{owner}/{repo}/codespaces/new");
    private static final PathTemplate REPO_CODESPACES_PERMISSIONS_CHECK =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/permissions_check");
    private static final PathTemplate REPO_PULL_CODESPACES =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/codespaces");
    private static final PathTemplate USER_CODESPACES = PathTemplate.of("/user/codespaces");
    private static final PathTemplate USER_CODESPACE = PathTemplate.of("/user/codespaces/{codespace_name}");
    private static final PathTemplate USER_CODESPACE_EXPORTS =
            PathTemplate.of("/user/codespaces/{codespace_name}/exports");
    private static final PathTemplate USER_CODESPACE_EXPORT =
            PathTemplate.of("/user/codespaces/{codespace_name}/exports/{export_id}");
    private static final PathTemplate USER_CODESPACE_PUBLISH =
            PathTemplate.of("/user/codespaces/{codespace_name}/publish");
    private static final PathTemplate USER_CODESPACE_START = PathTemplate.of("/user/codespaces/{codespace_name}/start");
    private static final PathTemplate USER_CODESPACE_STOP = PathTemplate.of("/user/codespaces/{codespace_name}/stop");

    /**
     * Codespaces resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespaceApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List codespaces in a repository for the authenticated user ({@code GET /repos/{owner}/{repo}/codespaces}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listInRepository(final String owner, final String repo) throws IOException {
        return listInRepository(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List codespaces in a repository for the authenticated user ({@code GET /repos/{owner}/{repo}/codespaces}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listInRepository(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_CODESPACES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesPage.class,
                        GitHubCodespacesPage::codespaces,
                        GitHubCodespacesPage::totalCount),
                owner,
                repo);
    }

    /**
     * Create a codespace in a repository ({@code POST /repos/{owner}/{repo}/codespaces}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload codespace attributes
     * @return created codespace
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace createWithRepo(
            final String owner, final String repo, final GitHubCreateWithRepoForAuthenticatedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(REPO_CODESPACES, owner, repo), payload), GitHubCodespace.class);
    }

    /**
     * List devcontainer configurations in a repository for the authenticated user ({@code GET
     * /repos/{owner}/{repo}/codespaces/devcontainers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of devcontainer configurations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubDevcontainer> listDevcontainersInRepository(final String owner, final String repo)
            throws IOException {
        return listDevcontainersInRepository(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List devcontainer configurations in a repository for the authenticated user ({@code GET
     * /repos/{owner}/{repo}/codespaces/devcontainers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of devcontainer configurations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubDevcontainer> listDevcontainersInRepository(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_CODESPACES_DEVCONTAINERS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubDevcontainersPage.class,
                        GitHubDevcontainersPage::devcontainers,
                        GitHubDevcontainersPage::totalCount),
                owner,
                repo);
    }

    /**
     * Get default attributes for a codespace ({@code GET /repos/{owner}/{repo}/codespaces/new}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return billable owner and default codespace attributes
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPreFlightWithRepoForAuthenticatedUserResponse preFlightWithRepo(final String owner, final String repo)
            throws IOException {
        return preFlightWithRepo(owner, repo, GitHubPreFlightWithRepoForAuthenticatedUserFilter.empty());
    }

    /**
     * Get default attributes for a codespace ({@code GET /repos/{owner}/{repo}/codespaces/new}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters ({@code ref}, {@code client_ip})
     * @return billable owner and default codespace attributes
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPreFlightWithRepoForAuthenticatedUserResponse preFlightWithRepo(
            final String owner, final String repo, final GitHubPreFlightWithRepoForAuthenticatedUserFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(REPO_CODESPACES_NEW, owner, repo).withQuery(filter.toQueryParameters()),
                GitHubPreFlightWithRepoForAuthenticatedUserResponse.class);
    }

    /**
     * Check whether the permissions defined by a devcontainer have been accepted by the authenticated user ({@code GET
     * /repos/{owner}/{repo}/codespaces/permissions_check}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters ({@code ref}, {@code devcontainer_path})
     * @return permission check result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespacesPermissionsCheckForDevcontainer checkPermissionsForDevcontainer(
            final String owner, final String repo, final GitHubCheckPermissionsForDevcontainerFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(REPO_CODESPACES_PERMISSIONS_CHECK, owner, repo).withQuery(filter.toQueryParameters()),
                GitHubCodespacesPermissionsCheckForDevcontainer.class);
    }

    /**
     * Create a codespace from a pull request ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/codespaces}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload codespace attributes
     * @return created codespace
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace createWithPullRequest(
            final String owner,
            final String repo,
            final int pullNumber,
            final GitHubCreateWithPrForAuthenticatedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_PULL_CODESPACES, owner, repo, pullNumber), payload), GitHubCodespace.class);
    }

    /**
     * List codespaces for the authenticated user ({@code GET /user/codespaces}).
     *
     * @return first HTTP page of codespaces
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubListForAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List codespaces for the authenticated user ({@code GET /user/codespaces}).
     *
     * @param filter query filters ({@code repository_id})
     * @return first HTTP page of codespaces
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listForAuthenticatedUser(final GitHubListForAuthenticatedUserFilter filter)
            throws IOException {
        return listForAuthenticatedUser(filter, GitHubPageRequest.byPage());
    }

    /**
     * List codespaces for the authenticated user ({@code GET /user/codespaces}).
     *
     * @param filter query filters ({@code repository_id})
     * @param request pagination token
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listForAuthenticatedUser(
            final GitHubListForAuthenticatedUserFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USER_CODESPACES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesPage.class,
                        GitHubCodespacesPage::codespaces,
                        GitHubCodespacesPage::totalCount));
    }

    /**
     * Create a codespace for the authenticated user ({@code POST /user/codespaces}).
     *
     * @param payload codespace attributes
     * @return created codespace
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace createForAuthenticatedUser(final GitHubCreateForAuthenticatedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_CODESPACES), payload), GitHubCodespace.class);
    }

    /**
     * Get a codespace for the authenticated user ({@code GET /user/codespaces/{codespace_name}}).
     *
     * @param codespaceName codespace name
     * @return codespace, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodespace> getForAuthenticatedUser(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(USER_CODESPACE, codespaceName), GitHubCodespace.class);
    }

    /**
     * Update a codespace for the authenticated user ({@code PATCH /user/codespaces/{codespace_name}}).
     *
     * @param codespaceName codespace name
     * @param payload attributes to update
     * @return updated codespace
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace updateForAuthenticatedUser(
            final String codespaceName, final GitHubUpdateForAuthenticatedUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(USER_CODESPACE, codespaceName), payload), GitHubCodespace.class);
    }

    /**
     * Delete a codespace for the authenticated user ({@code DELETE /user/codespaces/{codespace_name}}).
     *
     * @param codespaceName codespace name
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteForAuthenticatedUser(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(USER_CODESPACE, codespaceName));
    }

    /**
     * Export a codespace for the authenticated user ({@code POST /user/codespaces/{codespace_name}/exports}).
     *
     * @param codespaceName codespace name
     * @return export details with the ID to monitor
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespaceExportDetails exportForAuthenticatedUser(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(post(USER_CODESPACE_EXPORTS, codespaceName), GitHubCodespaceExportDetails.class);
    }

    /**
     * Get details about a codespace export ({@code GET /user/codespaces/{codespace_name}/exports/{export_id}}).
     *
     * @param codespaceName codespace name
     * @param exportId export ID
     * @return export details, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code codespaceName} or {@code exportId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodespaceExportDetails> getExportDetailsForAuthenticatedUser(
            final String codespaceName, final String exportId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(USER_CODESPACE_EXPORT, codespaceName, exportId), GitHubCodespaceExportDetails.class);
    }

    /**
     * Create a repository from an unpublished codespace ({@code POST /user/codespaces/{codespace_name}/publish}).
     *
     * @param codespaceName codespace name
     * @param payload repository name, visibility and default branch
     * @return codespace with its full repository
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespaceWithFullRepository publishForAuthenticatedUser(
            final String codespaceName, final GitHubPublishForAuthenticatedUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USER_CODESPACE_PUBLISH, codespaceName), payload),
                GitHubCodespaceWithFullRepository.class);
    }

    /**
     * Start a codespace for the authenticated user ({@code POST /user/codespaces/{codespace_name}/start}).
     *
     * @param codespaceName codespace name
     * @return started codespace
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace startForAuthenticatedUser(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(post(USER_CODESPACE_START, codespaceName), GitHubCodespace.class);
    }

    /**
     * Stop a codespace for the authenticated user ({@code POST /user/codespaces/{codespace_name}/stop}).
     *
     * @param codespaceName codespace name
     * @return stopped codespace
     * @throws IllegalArgumentException if {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace stopForAuthenticatedUser(final String codespaceName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(post(USER_CODESPACE_STOP, codespaceName), GitHubCodespace.class);
    }
}
