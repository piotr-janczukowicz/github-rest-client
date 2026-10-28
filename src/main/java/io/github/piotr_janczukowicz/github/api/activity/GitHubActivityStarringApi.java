package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/** GitHub activity starring REST resource for API version {@code 2026-03-10}. */
public final class GitHubActivityStarringApi extends GitHubResourceApi {

    private static final PathTemplate STARGAZERS = PathTemplate.of("/repos/{owner}/{repo}/stargazers");
    private static final PathTemplate STARGAZERS_COUNT = PathTemplate.of("/repos/{owner}/{repo}/stargazers/count");
    private static final PathTemplate STARGAZERS_HISTORY = PathTemplate.of("/repos/{owner}/{repo}/stargazers/history");
    private static final PathTemplate USER_STARRED = PathTemplate.of("/user/starred");
    private static final PathTemplate USER_STARRED_REPO = PathTemplate.of("/user/starred/{owner}/{repo}");
    private static final PathTemplate USERS_STARRED = PathTemplate.of("/users/{username}/starred");

    private static final String STAR_MEDIA_TYPE = "application/vnd.github.star+json";

    private static final JsonType<List<GitHubSimpleUser>> SIMPLE_USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);
    private static final JsonType<List<GitHubRepository>> REPOSITORIES_TYPE = JsonTypes.listOf(GitHubRepository.class);
    private static final JsonType<List<GitHubStargazer>> STARGAZERS_TYPE = JsonTypes.listOf(GitHubStargazer.class);
    private static final JsonType<List<GitHubStarredRepository>> STARRED_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubStarredRepository.class);
    private static final JsonType<List<GitHubStargazerHistory>> STARGAZER_HISTORY_TYPE =
            JsonTypes.listOf(GitHubStargazerHistory.class);

    /** Endpoint maximum for {@code per_page} on the stargazer history list. */
    private static final int STARGAZER_HISTORY_MAX_PER_PAGE = 30;

    private static final GitHubListSpec<GitHubStargazerHistory> STARGAZER_HISTORY_SPEC =
            GitHubListSpec.pages(STARGAZER_HISTORY_TYPE).withMaxPerPage(STARGAZER_HISTORY_MAX_PER_PAGE);

    /**
     * Starring resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityStarringApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List stargazers with starred-at timestamps ({@code GET /repos/{owner}/{repo}/stargazers}) using the
     * {@code application/vnd.github.star+json} media type.
     *
     * <p>Sends {@code Accept: application/vnd.github.star+json} so each item carries {@code starred_at}. See
     * {@link #listStargazersWithStarredAt(String, String, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of stargazers
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStargazer> listStargazersWithStarredAt(final String owner, final String repo)
            throws IOException {
        return listStargazersWithStarredAt(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List stargazers with starred-at timestamps ({@code GET /repos/{owner}/{repo}/stargazers}) using the
     * {@code application/vnd.github.star+json} media type.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of stargazers
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStargazer> listStargazersWithStarredAt(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                STARGAZERS, Map.of(), request, GitHubListSpec.pages(STARGAZERS_TYPE), starMediaHeaders(), owner, repo);
    }

    /**
     * Get the stargazer count of a repository ({@code GET /repos/{owner}/{repo}/stargazers/count}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return stargazer count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubGetStargazerCountForRepoResponse getStargazerCountForRepo(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(STARGAZERS_COUNT, owner, repo), GitHubGetStargazerCountForRepoResponse.class);
    }

    /**
     * Get the star history of a repository ({@code GET /repos/{owner}/{repo}/stargazers/history}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of weekly star history
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubStargazerHistory> getStargazerHistoryForRepo(final String owner, final String repo)
            throws IOException {
        return getStargazerHistoryForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * Get the star history of a repository ({@code GET /repos/{owner}/{repo}/stargazers/history}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of weekly star history
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubStargazerHistory> getStargazerHistoryForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(STARGAZERS_HISTORY, Map.of(), request, STARGAZER_HISTORY_SPEC, owner, repo);
    }

    /**
     * List repositories starred by the authenticated user ({@code GET /user/starred}).
     *
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByAuthenticatedUser() throws IOException {
        return listReposStarredByAuthenticatedUser(
                GitHubListReposStarredByAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repositories starred by the authenticated user ({@code GET /user/starred}).
     *
     * @param filter query filters
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByAuthenticatedUser(
            final GitHubListReposStarredByAuthenticatedUserFilter filter) throws IOException {
        return listReposStarredByAuthenticatedUser(filter, GitHubPageRequest.byPage());
    }

    /**
     * List repositories starred by the authenticated user ({@code GET /user/starred}).
     *
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        return listReposStarredByAuthenticatedUser(GitHubListReposStarredByAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List repositories starred by the authenticated user ({@code GET /user/starred}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByAuthenticatedUser(
            final GitHubListReposStarredByAuthenticatedUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_STARRED, filter.toQueryParameters(), request, REPOSITORIES_TYPE);
    }

    /**
     * List repositories starred by the authenticated user with starred-at timestamps ({@code GET /user/starred}) using
     * the {@code application/vnd.github.star+json} media type.
     *
     * @return HTTP page of starred repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByAuthenticatedUserWithStarredAt() throws IOException {
        return listReposStarredByAuthenticatedUserWithStarredAt(GitHubPageRequest.byPage());
    }

    /**
     * List repositories starred by the authenticated user with starred-at timestamps ({@code GET /user/starred}) using
     * the {@code application/vnd.github.star+json} media type.
     *
     * @param request pagination token
     * @return HTTP page of starred repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByAuthenticatedUserWithStarredAt(
            final GitHubPageRequest request) throws IOException {
        return listReposStarredByAuthenticatedUserWithStarredAt(
                GitHubListReposStarredByAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List repositories starred by the authenticated user with starred-at timestamps ({@code GET /user/starred}) using
     * the {@code application/vnd.github.star+json} media type.
     *
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of starred repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByAuthenticatedUserWithStarredAt(
            final GitHubListReposStarredByAuthenticatedUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USER_STARRED,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.pages(STARRED_REPOSITORIES_TYPE),
                starMediaHeaders());
    }

    /**
     * Check if a repository is starred by the authenticated user ({@code GET /user/starred/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return {@code true} on HTTP 204, {@code false} on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public boolean checkRepoIsStarredByAuthenticatedUser(final String owner, final String repo) throws IOException {
        return caller.isExisting(get(USER_STARRED_REPO, owner, repo));
    }

    /**
     * Star a repository for the authenticated user ({@code PUT /user/starred/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void starRepoForAuthenticatedUser(final String owner, final String repo) throws IOException {
        caller.send(put(USER_STARRED_REPO, owner, repo));
    }

    /**
     * Unstar a repository for the authenticated user ({@code DELETE /user/starred/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unstarRepoForAuthenticatedUser(final String owner, final String repo) throws IOException {
        caller.send(delete(USER_STARRED_REPO, owner, repo));
    }

    /**
     * List repositories starred by a user with starred-at timestamps ({@code GET /users/{username}/starred}) using the
     * {@code application/vnd.github.star+json} media type.
     *
     * @param username user login
     * @return first HTTP page of starred repositories
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByUserWithStarredAt(final String username)
            throws IOException {
        return listReposStarredByUserWithStarredAt(
                username, GitHubListReposStarredByUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repositories starred by a user with starred-at timestamps ({@code GET /users/{username}/starred}) using the
     * {@code application/vnd.github.star+json} media type.
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of starred repositories
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByUserWithStarredAt(
            final String username, final GitHubPageRequest request) throws IOException {
        return listReposStarredByUserWithStarredAt(username, GitHubListReposStarredByUserFilter.empty(), request);
    }

    /**
     * List repositories starred by a user with starred-at timestamps ({@code GET /users/{username}/starred}) using the
     * {@code application/vnd.github.star+json} media type.
     *
     * @param username user login
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of starred repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubStarredRepository> listReposStarredByUserWithStarredAt(
            final String username, final GitHubListReposStarredByUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_STARRED,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.pages(STARRED_REPOSITORIES_TYPE),
                starMediaHeaders(),
                username);
    }

    /**
     * List stargazers ({@code GET /repos/{owner}/{repo}/stargazers}) without the star+json media type.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of users
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listStargazersForRepo(final String owner, final String repo)
            throws IOException {
        return listStargazersForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List stargazers ({@code GET /repos/{owner}/{repo}/stargazers}) without the star+json media type.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of users
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listStargazersForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(STARGAZERS, Map.of(), request, SIMPLE_USERS_TYPE, owner, repo);
    }

    /**
     * List repositories starred by a user ({@code GET /users/{username}/starred}) without the star+json media type.
     *
     * @param username user login
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByUser(final String username) throws IOException {
        return listReposStarredByUser(username, GitHubListReposStarredByUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repositories starred by a user ({@code GET /users/{username}/starred}) without the star+json media type.
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByUser(final String username, final GitHubPageRequest request)
            throws IOException {
        return listReposStarredByUser(username, GitHubListReposStarredByUserFilter.empty(), request);
    }

    /**
     * List repositories starred by a user ({@code GET /users/{username}/starred}) without the star+json media type.
     *
     * @param username user login
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listReposStarredByUser(
            final String username, final GitHubListReposStarredByUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_STARRED, filter.toQueryParameters(), request, REPOSITORIES_TYPE, username);
    }

    private static HttpHeaders starMediaHeaders() {
        return HttpHeaders.of(ACCEPT, STAR_MEDIA_TYPE);
    }
}
