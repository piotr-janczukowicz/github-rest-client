package io.github.piotr_janczukowicz.github.api.repositories;

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
import org.jspecify.annotations.Nullable;

/** GitHub repository forks REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesForksApi extends GitHubResourceApi {

    private static final PathTemplate FORKS = PathTemplate.of("/repos/{owner}/{repo}/forks");

    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    /**
     * Forks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesForksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List forks ({@code GET /repos/{owner}/{repo}/forks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of forks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForks(final String owner, final String repo) throws IOException {
        return listForks(owner, repo, GitHubListForksFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List forks ({@code GET /repos/{owner}/{repo}/forks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of forks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForks(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listForks(owner, repo, GitHubListForksFilter.empty(), request);
    }

    /**
     * List forks ({@code GET /repos/{owner}/{repo}/forks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of forks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForks(
            final String owner, final String repo, final GitHubListForksFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(FORKS, filter.toQueryParameters(), request, MINIMAL_REPOSITORIES_TYPE, owner, repo);
    }

    /**
     * Create a fork ({@code POST /repos/{owner}/{repo}/forks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload fork options
     * @return forked repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFullRepository createFork(
            final String owner, final String repo, final @Nullable GitHubCreateForkPayload payload) throws IOException {

        return caller.fetch(caller.jsonIfPresent(post(FORKS, owner, repo), payload), GitHubFullRepository.class);
    }
}
