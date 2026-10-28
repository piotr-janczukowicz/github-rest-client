package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * GitHub Dependabot alert dismissal requests REST resource for API version {@code 2026-03-10}.
 *
 * <p>List, read, create, review and cancel dismissal requests for Dependabot alerts.
 */
public final class GitHubDependabotAlertDismissalRequestsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_DISMISSAL_REQUESTS =
            PathTemplate.of("/orgs/{org}/dismissal-requests/dependabot");
    private static final PathTemplate REPO_DISMISSAL_REQUESTS =
            PathTemplate.of("/repos/{owner}/{repo}/dismissal-requests/dependabot");
    private static final PathTemplate REPO_DISMISSAL_REQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/dismissal-requests/dependabot/{alert_number}");

    private static final JsonType<List<GitHubDependabotDismissalRequest>> DISMISSAL_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubDependabotDismissalRequest.class);

    /**
     * Dependabot alert dismissal requests resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotAlertDismissalRequestsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List dismissal requests for an organization ({@code GET /orgs/{org}/dismissal-requests/dependabot}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listDismissalRequestsForOrg(String,
     * GitHubListDismissalRequestsForOrgFilter, GitHubPageRequest)}.
     *
     * @param org organization login
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubDependabotDismissalRequest> listDismissalRequestsForOrg(final String org)
            throws IOException {
        return listDismissalRequestsForOrg(
                org, GitHubListDismissalRequestsForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List dismissal requests for an organization ({@code GET /orgs/{org}/dismissal-requests/dependabot}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request page pagination token
     * @return page of dismissal requests
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubDependabotDismissalRequest> listDismissalRequestsForOrg(
            final String org, final GitHubListDismissalRequestsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_DISMISSAL_REQUESTS, filter.toQueryParameters(), request, DISMISSAL_REQUESTS_TYPE, org);
    }

    /**
     * List dismissal requests for a repository ({@code GET /repos/{owner}/{repo}/dismissal-requests/dependabot}) with
     * no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listDismissalRequestsForRepo(String, String,
     * GitHubListDismissalRequestsForRepoFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubDependabotDismissalRequest> listDismissalRequestsForRepo(
            final String owner, final String repo) throws IOException {
        return listDismissalRequestsForRepo(
                owner, repo, GitHubListDismissalRequestsForRepoFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List dismissal requests for a repository ({@code GET /repos/{owner}/{repo}/dismissal-requests/dependabot}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request page pagination token
     * @return page of dismissal requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubDependabotDismissalRequest> listDismissalRequestsForRepo(
            final String owner,
            final String repo,
            final GitHubListDismissalRequestsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                REPO_DISMISSAL_REQUESTS, filter.toQueryParameters(), request, DISMISSAL_REQUESTS_TYPE, owner, repo);
    }

    /**
     * Get a dismissal request for a Dependabot alert ({@code GET
     * /repos/{owner}/{repo}/dismissal-requests/dependabot/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return the dismissal request, or empty if GitHub has no such request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubDependabotDismissalRequest> getDismissalRequest(
            final String owner, final String repo, final int alertNumber) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(REPO_DISMISSAL_REQUEST, owner, repo, alertNumber), GitHubDependabotDismissalRequest.class);
    }

    /**
     * Create a dismissal request for a Dependabot alert ({@code POST
     * /repos/{owner}/{repo}/dismissal-requests/dependabot/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param payload reason and optional comment of the dismissal
     * @return the created dismissal request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubDependabotDismissalRequest createDismissalRequest(
            final String owner,
            final String repo,
            final int alertNumber,
            final GitHubCreateDismissalRequestPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_DISMISSAL_REQUEST, owner, repo, alertNumber), payload),
                GitHubDependabotDismissalRequest.class);
    }

    /**
     * Review a dismissal request for a Dependabot alert ({@code PATCH
     * /repos/{owner}/{repo}/dismissal-requests/dependabot/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number of the dismissal request
     * @param payload review action and message
     * @return the review identifier
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubReviewDismissalRequestResult reviewDismissalRequest(
            final String owner,
            final String repo,
            final int alertNumber,
            final GitHubReviewDismissalRequestPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_DISMISSAL_REQUEST, owner, repo, alertNumber), payload),
                GitHubReviewDismissalRequestResult.class);
    }

    /**
     * Cancel a dismissal request for a Dependabot alert ({@code DELETE
     * /repos/{owner}/{repo}/dismissal-requests/dependabot/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number of the dismissal request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void cancelDismissalRequest(final String owner, final String repo, final int alertNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(REPO_DISMISSAL_REQUEST, owner, repo, alertNumber));
    }
}
