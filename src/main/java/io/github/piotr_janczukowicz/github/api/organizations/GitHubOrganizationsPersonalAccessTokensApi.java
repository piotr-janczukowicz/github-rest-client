package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/** GitHub organization personal access tokens REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsPersonalAccessTokensApi extends GitHubResourceApi {

    private static final PathTemplate PERSONAL_ACCESS_TOKEN_REQUESTS =
            PathTemplate.of("/orgs/{org}/personal-access-token-requests");
    private static final PathTemplate PERSONAL_ACCESS_TOKEN_REQUEST =
            PathTemplate.of("/orgs/{org}/personal-access-token-requests/{pat_request_id}");
    private static final PathTemplate PERSONAL_ACCESS_TOKEN_REQUEST_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/personal-access-token-requests/{pat_request_id}/repositories");
    private static final PathTemplate PERSONAL_ACCESS_TOKENS = PathTemplate.of("/orgs/{org}/personal-access-tokens");
    private static final PathTemplate PERSONAL_ACCESS_TOKEN =
            PathTemplate.of("/orgs/{org}/personal-access-tokens/{pat_id}");
    private static final PathTemplate PERSONAL_ACCESS_TOKEN_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/personal-access-tokens/{pat_id}/repositories");

    private static final JsonType<List<GitHubOrganizationPatGrantRequest>> PAT_GRANT_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationPatGrantRequest.class);
    private static final JsonType<List<GitHubOrganizationPatGrant>> PAT_GRANTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationPatGrant.class);
    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    /**
     * Personal access tokens resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsPersonalAccessTokensApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List requests to access organization resources with fine-grained personal access tokens ({@code GET
     * /orgs/{org}/personal-access-token-requests}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrantRequests(String,
     * GitHubOrganizationsPersonalAccessTokensFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of personal access token grant requests
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrantRequest> listPatGrantRequests(final String org) throws IOException {
        return listPatGrantRequests(
                org, GitHubOrganizationsPersonalAccessTokensFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List requests to access organization resources with fine-grained personal access tokens ({@code GET
     * /orgs/{org}/personal-access-token-requests}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrantRequests(String,
     * GitHubOrganizationsPersonalAccessTokensFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of personal access token grant requests
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrantRequest> listPatGrantRequests(
            final String org, final GitHubOrganizationsPersonalAccessTokensFilter filter) throws IOException {
        return listPatGrantRequests(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List requests to access organization resources with fine-grained personal access tokens ({@code GET
     * /orgs/{org}/personal-access-token-requests}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of personal access token grant requests
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrantRequest> listPatGrantRequests(
            final String org, final GitHubPageRequest request) throws IOException {
        return listPatGrantRequests(org, GitHubOrganizationsPersonalAccessTokensFilter.empty(), request);
    }

    /**
     * List requests to access organization resources with fine-grained personal access tokens ({@code GET
     * /orgs/{org}/personal-access-token-requests}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of personal access token grant requests
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrantRequest> listPatGrantRequests(
            final String org,
            final GitHubOrganizationsPersonalAccessTokensFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return listValues(
                PERSONAL_ACCESS_TOKEN_REQUESTS,
                filter.toQueryValues(),
                request,
                GitHubListSpec.pages(PAT_GRANT_REQUESTS_TYPE),
                org);
    }

    /**
     * Review requests to access organization resources with fine-grained personal access tokens ({@code POST
     * /orgs/{org}/personal-access-token-requests}).
     *
     * <p>Succeeds on HTTP 202. The accepted response body is an opaque JSON object and is ignored.
     *
     * @param org organization name
     * @param payload review request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reviewPatGrantRequestsInBulk(final String org, final GitHubReviewPatGrantRequestsInBulkPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(PERSONAL_ACCESS_TOKEN_REQUESTS, org), payload));
    }

    /**
     * Review a request to access organization resources with a fine-grained personal access token ({@code POST
     * /orgs/{org}/personal-access-token-requests/{pat_request_id}}).
     *
     * <p>Succeeds on HTTP 204 with an empty body.
     *
     * @param org organization name
     * @param patRequestId personal access token grant request id
     * @param payload review request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void reviewPatGrantRequest(
            final String org, final long patRequestId, final GitHubReviewPatGrantRequestPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(PERSONAL_ACCESS_TOKEN_REQUEST, org, patRequestId), payload));
    }

    /**
     * List repositories requested to be accessed by a fine-grained personal access token ({@code GET
     * /orgs/{org}/personal-access-token-requests/{pat_request_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrantRequestRepositories(String, long,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param patRequestId personal access token grant request id
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPatGrantRequestRepositories(
            final String org, final long patRequestId) throws IOException {
        return listPatGrantRequestRepositories(org, patRequestId, GitHubPageRequest.byPage());
    }

    /**
     * List repositories requested to be accessed by a fine-grained personal access token ({@code GET
     * /orgs/{org}/personal-access-token-requests/{pat_request_id}/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param patRequestId personal access token grant request id
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPatGrantRequestRepositories(
            final String org, final long patRequestId, final GitHubPageRequest request) throws IOException {

        return pageList(
                PERSONAL_ACCESS_TOKEN_REQUEST_REPOSITORIES,
                Map.of(),
                request,
                MINIMAL_REPOSITORIES_TYPE,
                org,
                patRequestId);
    }

    /**
     * List fine-grained personal access tokens with access to organization resources ({@code GET
     * /orgs/{org}/personal-access-tokens}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrants(String,
     * GitHubOrganizationsPersonalAccessTokensFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of personal access token grants
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrant> listPatGrants(final String org) throws IOException {
        return listPatGrants(org, GitHubOrganizationsPersonalAccessTokensFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List fine-grained personal access tokens with access to organization resources ({@code GET
     * /orgs/{org}/personal-access-tokens}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrants(String,
     * GitHubOrganizationsPersonalAccessTokensFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of personal access token grants
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrant> listPatGrants(
            final String org, final GitHubOrganizationsPersonalAccessTokensFilter filter) throws IOException {
        return listPatGrants(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List fine-grained personal access tokens with access to organization resources ({@code GET
     * /orgs/{org}/personal-access-tokens}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of personal access token grants
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrant> listPatGrants(final String org, final GitHubPageRequest request)
            throws IOException {
        return listPatGrants(org, GitHubOrganizationsPersonalAccessTokensFilter.empty(), request);
    }

    /**
     * List fine-grained personal access tokens with access to organization resources ({@code GET
     * /orgs/{org}/personal-access-tokens}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of personal access token grants
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationPatGrant> listPatGrants(
            final String org,
            final GitHubOrganizationsPersonalAccessTokensFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return listValues(
                PERSONAL_ACCESS_TOKENS, filter.toQueryValues(), request, GitHubListSpec.pages(PAT_GRANTS_TYPE), org);
    }

    /**
     * Update the access to organization resources via fine-grained personal access tokens ({@code POST
     * /orgs/{org}/personal-access-tokens}).
     *
     * <p>Succeeds on HTTP 202. The accepted response body is an opaque JSON object and is ignored.
     *
     * @param org organization name
     * @param payload update request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updatePatAccesses(final String org, final GitHubUpdatePatAccessesPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(PERSONAL_ACCESS_TOKENS, org), payload));
    }

    /**
     * Update the access a fine-grained personal access token has to organization resources ({@code POST
     * /orgs/{org}/personal-access-tokens/{pat_id}}).
     *
     * <p>Succeeds on HTTP 204 with an empty body.
     *
     * @param org organization name
     * @param patId fine-grained personal access token id
     * @param payload update request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updatePatAccess(final String org, final long patId, final GitHubUpdatePatAccessPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(PERSONAL_ACCESS_TOKEN, org, patId), payload));
    }

    /**
     * List repositories a fine-grained personal access token has access to ({@code GET
     * /orgs/{org}/personal-access-tokens/{pat_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPatGrantRepositories(String, long,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param patId fine-grained personal access token id
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPatGrantRepositories(final String org, final long patId)
            throws IOException {
        return listPatGrantRepositories(org, patId, GitHubPageRequest.byPage());
    }

    /**
     * List repositories a fine-grained personal access token has access to ({@code GET
     * /orgs/{org}/personal-access-tokens/{pat_id}/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param patId fine-grained personal access token id
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPatGrantRepositories(
            final String org, final long patId, final GitHubPageRequest request) throws IOException {

        return pageList(PERSONAL_ACCESS_TOKEN_REPOSITORIES, Map.of(), request, MINIMAL_REPOSITORIES_TYPE, org, patId);
    }
}
