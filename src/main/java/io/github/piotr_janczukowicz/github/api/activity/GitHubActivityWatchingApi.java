package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositorySubscription;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
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
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub activity watching REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubActivityWatchingApi extends GitHubResourceApi {

    private static final PathTemplate REPO_SUBSCRIBERS = PathTemplate.of("/repos/{owner}/{repo}/subscribers");
    private static final PathTemplate REPO_SUBSCRIPTION = PathTemplate.of("/repos/{owner}/{repo}/subscription");
    private static final PathTemplate USER_SUBSCRIPTIONS = PathTemplate.of("/user/subscriptions");
    private static final PathTemplate USERS_SUBSCRIPTIONS = PathTemplate.of("/users/{username}/subscriptions");

    private static final JsonType<List<GitHubSimpleUser>> SIMPLE_USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);
    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    /**
     * Watching resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityWatchingApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List watchers of a repository ({@code GET /repos/{owner}/{repo}/subscribers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of users
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listWatchersForRepo(final String owner, final String repo) throws IOException {
        return listWatchersForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List watchers of a repository ({@code GET /repos/{owner}/{repo}/subscribers}).
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
    public GitHubPage<GitHubSimpleUser> listWatchersForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_SUBSCRIBERS, Map.of(), request, SIMPLE_USERS_TYPE, owner, repo);
    }

    /**
     * Get a repository subscription ({@code GET /repos/{owner}/{repo}/subscription}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return subscription, or empty on HTTP 404 (not watching)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRepositorySubscription> getRepoSubscription(final String owner, final String repo)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_SUBSCRIPTION, owner, repo), GitHubRepositorySubscription.class);
    }

    /**
     * Set a repository subscription ({@code PUT /repos/{owner}/{repo}/subscription}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload optional subscription settings; {@code null} sends no body
     * @return repository subscription
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositorySubscription setRepoSubscription(
            final String owner, final String repo, final @Nullable GitHubSetRepoSubscriptionPayload payload)
            throws IOException {
        return caller.fetch(
                caller.jsonIfPresent(put(REPO_SUBSCRIPTION, owner, repo), payload), GitHubRepositorySubscription.class);
    }

    /**
     * Set a repository subscription without a request body ({@code PUT /repos/{owner}/{repo}/subscription}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return repository subscription
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositorySubscription setRepoSubscription(final String owner, final String repo) throws IOException {
        return setRepoSubscription(owner, repo, null);
    }

    /**
     * Delete a repository subscription ({@code DELETE /repos/{owner}/{repo}/subscription}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRepoSubscription(final String owner, final String repo) throws IOException {
        caller.send(delete(REPO_SUBSCRIPTION, owner, repo));
    }

    /**
     * List repositories watched by the authenticated user ({@code GET /user/subscriptions}).
     *
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listWatchedReposForAuthenticatedUser() throws IOException {
        return listWatchedReposForAuthenticatedUser(GitHubPageRequest.byPage());
    }

    /**
     * List repositories watched by the authenticated user ({@code GET /user/subscriptions}).
     *
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listWatchedReposForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_SUBSCRIPTIONS, Map.of(), request, MINIMAL_REPOSITORIES_TYPE);
    }

    /**
     * List repositories watched by a user ({@code GET /users/{username}/subscriptions}).
     *
     * @param username user login
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listReposWatchedByUser(final String username) throws IOException {
        return listReposWatchedByUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List repositories watched by a user ({@code GET /users/{username}/subscriptions}).
     *
     * @param username user login
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listReposWatchedByUser(
            final String username, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_SUBSCRIPTIONS, Map.of(), request, MINIMAL_REPOSITORIES_TYPE, username);
    }
}
