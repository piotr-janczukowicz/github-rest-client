package io.github.piotr_janczukowicz.github.api.pullrequests;

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
import java.util.Optional;

/** GitHub pull request stacks REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsStacksApi extends GitHubResourceApi {

    private static final PathTemplate STACK_UNSTACK =
            PathTemplate.of("/repos/{owner}/{repo}/stacks/{stack_number}/unstack");
    private static final PathTemplate STACK_ADD = PathTemplate.of("/repos/{owner}/{repo}/stacks/{stack_number}/add");
    private static final PathTemplate STACK = PathTemplate.of("/repos/{owner}/{repo}/stacks/{stack_number}");
    private static final PathTemplate STACKS = PathTemplate.of("/repos/{owner}/{repo}/stacks");

    private static final JsonType<List<GitHubPullRequestStackMinimal>> STACKS_TYPE =
            JsonTypes.listOf(GitHubPullRequestStackMinimal.class);

    /**
     * Stacks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsStacksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List pull request stacks ({@code GET /repos/{owner}/{repo}/stacks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of stacks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubPullRequestStackMinimal> list(final String owner, final String repo) throws IOException {
        return list(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List pull request stacks ({@code GET /repos/{owner}/{repo}/stacks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of stacks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubPullRequestStackMinimal> list(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return list(owner, repo, GitHubListPullRequestStacksFilter.empty(), request);
    }

    /**
     * List pull request stacks ({@code GET /repos/{owner}/{repo}/stacks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of stacks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubPullRequestStackMinimal> list(
            final String owner,
            final String repo,
            final GitHubListPullRequestStacksFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(STACKS, filter.toQueryParameters(), request, STACKS_TYPE, owner, repo);
    }

    /**
     * Create a pull request stack ({@code POST /repos/{owner}/{repo}/stacks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload stack to create
     * @return created stack
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestStackDetail create(
            final String owner, final String repo, final GitHubCreatePullRequestStackPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(STACKS, owner, repo), payload), GitHubPullRequestStackDetail.class);
    }

    /**
     * Get a pull request stack ({@code GET /repos/{owner}/{repo}/stacks/{stack_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param stackNumber stack number
     * @return stack, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubPullRequestStackDetail> get(final String owner, final String repo, final int stackNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(get(STACK, owner, repo, stackNumber), GitHubPullRequestStackDetail.class);
    }

    /**
     * Add pull requests to a stack ({@code POST /repos/{owner}/{repo}/stacks/{stack_number}/add}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param stackNumber stack number
     * @param payload pull requests to add
     * @return updated stack
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestStackDetail add(
            final String owner,
            final String repo,
            final int stackNumber,
            final GitHubAddPullRequestsToStackPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(STACK_ADD, owner, repo, stackNumber), payload), GitHubPullRequestStackDetail.class);
    }

    /**
     * Remove pull requests from a stack ({@code POST /repos/{owner}/{repo}/stacks/{stack_number}/unstack}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param stackNumber stack number
     * @return updated stack when pull requests remain (HTTP 200), or empty when the stack is dissolved (HTTP 204)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubPullRequestStackDetail> unstack(final String owner, final String repo, final int stackNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchUnless(
                post(STACK_UNSTACK, owner, repo, stackNumber),
                GitHubPullRequestStackDetail.class,
                HttpStatus.NO_CONTENT);
    }
}
