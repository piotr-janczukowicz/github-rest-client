package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubPushRuleBypassRequest;
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
 * GitHub repository bypass requests REST resource for API version {@code 2026-03-10}.
 *
 * <p>List and read push rule bypass requests of a repository.
 */
public final class GitHubRepositoriesBypassRequestsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_PUSH_RULE_BYPASS_REQUESTS =
            PathTemplate.of("/repos/{owner}/{repo}/bypass-requests/push-rules");
    private static final PathTemplate REPO_PUSH_RULE_BYPASS_REQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/bypass-requests/push-rules/{bypass_request_number}");

    private static final JsonType<List<GitHubPushRuleBypassRequest>> BYPASS_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubPushRuleBypassRequest.class);

    /**
     * Repository bypass requests resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesBypassRequestsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List push rule bypass requests for a repository ({@code GET /repos/{owner}/{repo}/bypass-requests/push-rules})
     * with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listRepoPushBypassRequests(String, String,
     * GitHubListRepoPushBypassRequestsFilter, GitHubPageRequest)}.
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
    public GitHubPage<GitHubPushRuleBypassRequest> listRepoPushBypassRequests(final String owner, final String repo)
            throws IOException {
        return listRepoPushBypassRequests(
                owner, repo, GitHubListRepoPushBypassRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List push rule bypass requests for a repository ({@code GET /repos/{owner}/{repo}/bypass-requests/push-rules}).
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
    public GitHubPage<GitHubPushRuleBypassRequest> listRepoPushBypassRequests(
            final String owner,
            final String repo,
            final GitHubListRepoPushBypassRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                REPO_PUSH_RULE_BYPASS_REQUESTS, filter.toQueryParameters(), request, BYPASS_REQUESTS_TYPE, owner, repo);
    }

    /**
     * Get a push rule bypass request for a repository ({@code GET
     * /repos/{owner}/{repo}/bypass-requests/push-rules/{bypass_request_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param bypassRequestNumber bypass request number
     * @return the bypass request, or empty if GitHub has no such request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubPushRuleBypassRequest> getRepoPushBypassRequest(
            final String owner, final String repo, final int bypassRequestNumber) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(REPO_PUSH_RULE_BYPASS_REQUEST, owner, repo, bypassRequestNumber),
                GitHubPushRuleBypassRequest.class);
    }
}
