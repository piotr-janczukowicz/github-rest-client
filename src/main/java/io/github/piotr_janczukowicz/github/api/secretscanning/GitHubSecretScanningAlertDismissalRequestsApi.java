package io.github.piotr_janczukowicz.github.api.secretscanning;

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
 * GitHub Secret Scanning alert dismissal requests REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read and review requests to dismiss secret scanning alerts for enterprises, organizations and repositories.
 */
public final class GitHubSecretScanningAlertDismissalRequestsApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_DISMISSAL_REQUESTS =
            PathTemplate.of("/enterprises/{enterprise}/dismissal-requests/secret-scanning");
    private static final PathTemplate ORG_DISMISSAL_REQUESTS =
            PathTemplate.of("/orgs/{org}/dismissal-requests/secret-scanning");
    private static final PathTemplate REPO_DISMISSAL_REQUESTS =
            PathTemplate.of("/repos/{owner}/{repo}/dismissal-requests/secret-scanning");
    private static final PathTemplate REPO_DISMISSAL_REQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/dismissal-requests/secret-scanning/{alert_number}");

    private static final JsonType<List<GitHubSecretScanningDismissalRequest>> DISMISSAL_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningDismissalRequest.class);

    /**
     * Secret scanning alert dismissal requests resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningAlertDismissalRequestsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List enterprise dismissal requests ({@code GET /enterprises/{enterprise}/dismissal-requests/secret-scanning})
     * with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEnterpriseDismissalRequests(String,
     * GitHubListEnterpriseDismissalRequestsFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningDismissalRequest> listEnterpriseDismissalRequests(final String enterprise)
            throws IOException {
        return listEnterpriseDismissalRequests(
                enterprise, GitHubListEnterpriseDismissalRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List enterprise dismissal requests ({@code GET /enterprises/{enterprise}/dismissal-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningDismissalRequest> listEnterpriseDismissalRequests(
            final String enterprise, final GitHubListEnterpriseDismissalRequestsFilter filter) throws IOException {
        return listEnterpriseDismissalRequests(enterprise, filter, GitHubPageRequest.byPage());
    }

    /**
     * List enterprise dismissal requests ({@code GET /enterprises/{enterprise}/dismissal-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request page pagination token
     * @return page of dismissal requests
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningDismissalRequest> listEnterpriseDismissalRequests(
            final String enterprise,
            final GitHubListEnterpriseDismissalRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ENTERPRISE_DISMISSAL_REQUESTS,
                filter.toQueryParameters(),
                request,
                DISMISSAL_REQUESTS_TYPE,
                enterprise);
    }

    /**
     * List organization dismissal requests ({@code GET /orgs/{org}/dismissal-requests/secret-scanning}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrgDismissalRequests(String,
     * GitHubListOrgDismissalRequestsFilter, GitHubPageRequest)}.
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
    public GitHubPage<GitHubSecretScanningDismissalRequest> listOrgDismissalRequests(final String org)
            throws IOException {
        return listOrgDismissalRequests(org, GitHubListOrgDismissalRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization dismissal requests ({@code GET /orgs/{org}/dismissal-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningDismissalRequest> listOrgDismissalRequests(
            final String org, final GitHubListOrgDismissalRequestsFilter filter) throws IOException {
        return listOrgDismissalRequests(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization dismissal requests ({@code GET /orgs/{org}/dismissal-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
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
    public GitHubPage<GitHubSecretScanningDismissalRequest> listOrgDismissalRequests(
            final String org, final GitHubListOrgDismissalRequestsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_DISMISSAL_REQUESTS, filter.toQueryParameters(), request, DISMISSAL_REQUESTS_TYPE, org);
    }

    /**
     * List repository dismissal requests ({@code GET /repos/{owner}/{repo}/dismissal-requests/secret-scanning}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listRepoDismissalRequests(String, String,
     * GitHubListRepoDismissalRequestsFilter, GitHubPageRequest)}.
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
    public GitHubPage<GitHubSecretScanningDismissalRequest> listRepoDismissalRequests(
            final String owner, final String repo) throws IOException {
        return listRepoDismissalRequests(
                owner, repo, GitHubListRepoDismissalRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository dismissal requests ({@code GET /repos/{owner}/{repo}/dismissal-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of dismissal requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningDismissalRequest> listRepoDismissalRequests(
            final String owner, final String repo, final GitHubListRepoDismissalRequestsFilter filter)
            throws IOException {
        return listRepoDismissalRequests(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List repository dismissal requests ({@code GET /repos/{owner}/{repo}/dismissal-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
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
    public GitHubPage<GitHubSecretScanningDismissalRequest> listRepoDismissalRequests(
            final String owner,
            final String repo,
            final GitHubListRepoDismissalRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                REPO_DISMISSAL_REQUESTS, filter.toQueryParameters(), request, DISMISSAL_REQUESTS_TYPE, owner, repo);
    }

    /**
     * Get a repository dismissal request ({@code GET
     * /repos/{owner}/{repo}/dismissal-requests/secret-scanning/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number of the dismissal request
     * @return the dismissal request, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubSecretScanningDismissalRequest> getDismissalRequest(
            final String owner, final String repo, final int alertNumber) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(REPO_DISMISSAL_REQUEST, owner, repo, alertNumber), GitHubSecretScanningDismissalRequest.class);
    }

    /**
     * Review a repository dismissal request ({@code PATCH
     * /repos/{owner}/{repo}/dismissal-requests/secret-scanning/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number of the dismissal request
     * @param payload review action and message
     * @return the review identifier
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
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
}
