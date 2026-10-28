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
 * GitHub Secret Scanning delegated bypass REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read and review secret scanning bypass requests, and dismiss bypass responses, for enterprises, organizations and
 * repositories.
 */
public final class GitHubSecretScanningDelegatedBypassApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_BYPASS_REQUESTS =
            PathTemplate.of("/enterprises/{enterprise}/bypass-requests/secret-scanning");
    private static final PathTemplate ORG_BYPASS_REQUESTS =
            PathTemplate.of("/orgs/{org}/bypass-requests/secret-scanning");
    private static final PathTemplate REPO_BYPASS_REQUESTS =
            PathTemplate.of("/repos/{owner}/{repo}/bypass-requests/secret-scanning");
    private static final PathTemplate REPO_BYPASS_REQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/bypass-requests/secret-scanning/{bypass_request_number}");
    private static final PathTemplate REPO_BYPASS_RESPONSE =
            PathTemplate.of("/repos/{owner}/{repo}/bypass-responses/secret-scanning/{bypass_response_id}");

    private static final JsonType<List<GitHubSecretScanningBypassRequest>> BYPASS_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningBypassRequest.class);

    /**
     * Secret scanning delegated bypass resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningDelegatedBypassApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List enterprise bypass requests ({@code GET /enterprises/{enterprise}/bypass-requests/secret-scanning}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEnterpriseBypassRequests(String,
     * GitHubListEnterpriseBypassRequestsFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listEnterpriseBypassRequests(final String enterprise)
            throws IOException {
        return listEnterpriseBypassRequests(
                enterprise, GitHubListEnterpriseBypassRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List enterprise bypass requests ({@code GET /enterprises/{enterprise}/bypass-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listEnterpriseBypassRequests(
            final String enterprise, final GitHubListEnterpriseBypassRequestsFilter filter) throws IOException {
        return listEnterpriseBypassRequests(enterprise, filter, GitHubPageRequest.byPage());
    }

    /**
     * List enterprise bypass requests ({@code GET /enterprises/{enterprise}/bypass-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request page pagination token
     * @return page of bypass requests
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listEnterpriseBypassRequests(
            final String enterprise,
            final GitHubListEnterpriseBypassRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ENTERPRISE_BYPASS_REQUESTS, filter.toQueryParameters(), request, BYPASS_REQUESTS_TYPE, enterprise);
    }

    /**
     * List organization bypass requests ({@code GET /orgs/{org}/bypass-requests/secret-scanning}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrgBypassRequests(String,
     * GitHubListOrgBypassRequestsFilter, GitHubPageRequest)}.
     *
     * @param org organization login
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listOrgBypassRequests(final String org) throws IOException {
        return listOrgBypassRequests(org, GitHubListOrgBypassRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization bypass requests ({@code GET /orgs/{org}/bypass-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listOrgBypassRequests(
            final String org, final GitHubListOrgBypassRequestsFilter filter) throws IOException {
        return listOrgBypassRequests(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization bypass requests ({@code GET /orgs/{org}/bypass-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request page pagination token
     * @return page of bypass requests
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listOrgBypassRequests(
            final String org, final GitHubListOrgBypassRequestsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_BYPASS_REQUESTS, filter.toQueryParameters(), request, BYPASS_REQUESTS_TYPE, org);
    }

    /**
     * List repository bypass requests ({@code GET /repos/{owner}/{repo}/bypass-requests/secret-scanning}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listRepoBypassRequests(String, String,
     * GitHubListRepoBypassRequestsFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listRepoBypassRequests(final String owner, final String repo)
            throws IOException {
        return listRepoBypassRequests(
                owner, repo, GitHubListRepoBypassRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository bypass requests ({@code GET /repos/{owner}/{repo}/bypass-requests/secret-scanning}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of bypass requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listRepoBypassRequests(
            final String owner, final String repo, final GitHubListRepoBypassRequestsFilter filter) throws IOException {
        return listRepoBypassRequests(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List repository bypass requests ({@code GET /repos/{owner}/{repo}/bypass-requests/secret-scanning}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request page pagination token
     * @return page of bypass requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSecretScanningBypassRequest> listRepoBypassRequests(
            final String owner,
            final String repo,
            final GitHubListRepoBypassRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_BYPASS_REQUESTS, filter.toQueryParameters(), request, BYPASS_REQUESTS_TYPE, owner, repo);
    }

    /**
     * Get a repository bypass request ({@code GET
     * /repos/{owner}/{repo}/bypass-requests/secret-scanning/{bypass_request_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param bypassRequestNumber bypass request number
     * @return the bypass request, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubSecretScanningBypassRequest> getBypassRequest(
            final String owner, final String repo, final int bypassRequestNumber) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(REPO_BYPASS_REQUEST, owner, repo, bypassRequestNumber), GitHubSecretScanningBypassRequest.class);
    }

    /**
     * Review a repository bypass request ({@code PATCH
     * /repos/{owner}/{repo}/bypass-requests/secret-scanning/{bypass_request_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param bypassRequestNumber bypass request number
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
    public GitHubReviewBypassRequestResult reviewBypassRequest(
            final String owner,
            final String repo,
            final int bypassRequestNumber,
            final GitHubReviewBypassRequestPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_BYPASS_REQUEST, owner, repo, bypassRequestNumber), payload),
                GitHubReviewBypassRequestResult.class);
    }

    /**
     * Dismiss a repository bypass response ({@code DELETE
     * /repos/{owner}/{repo}/bypass-responses/secret-scanning/{bypass_response_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param bypassResponseId bypass response ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void dismissBypassResponse(final String owner, final String repo, final long bypassResponseId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(REPO_BYPASS_RESPONSE, owner, repo, bypassResponseId));
    }
}
