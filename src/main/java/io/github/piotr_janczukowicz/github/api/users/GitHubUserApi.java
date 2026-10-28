package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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
import java.util.Optional;

/** GitHub users REST resource for API version {@code 2026-03-10}. */
public final class GitHubUserApi extends GitHubResourceApi {

    private static final PathTemplate USER_HOVERCARD = PathTemplate.of("/users/{username}/hovercard");
    private static final PathTemplate USER = PathTemplate.of("/users/{username}");
    private static final PathTemplate USER_BY_ACCOUNT_ID = PathTemplate.of("/user/{account_id}");
    private static final PathTemplate AUTHENTICATED_USER = PathTemplate.of("/user");
    private static final PathTemplate USERS = PathTemplate.of("/users");

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUserApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the authenticated user ({@code GET /user}).
     *
     * @return the user
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubUserView getAuthenticatedUser() throws IOException {
        return caller.fetch(get(AUTHENTICATED_USER), GitHubUserView.class);
    }

    /**
     * Update the authenticated user ({@code PATCH /user}).
     *
     * @param payload fields to send; null properties are omitted from JSON
     * @return the updated private user
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthenticatedUser updateAuthenticatedUser(final GitHubUpdateAuthenticatedUserPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(AUTHENTICATED_USER), payload), GitHubAuthenticatedUser.class);
    }

    /**
     * Get a user using their ID ({@code GET /user/{account_id}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param accountId numeric account id
     * @return the user, or empty if GitHub has no such account
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubUserView> getAccount(final long accountId) throws IOException {
        return caller.fetchOrEmpty(get(USER_BY_ACCOUNT_ID, accountId), GitHubUserView.class);
    }

    /**
     * List users ({@code GET /users}).
     *
     * <p>Uses {@link GitHubPageRequest#none()}. See {@link #list(GitHubPageRequest)}.
     *
     * @return first HTTP page of users
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> list() throws IOException {
        return list(GitHubPageRequest.none());
    }

    /**
     * List users ({@code GET /users}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest} only; page and cursor variants are
     * rejected. Pagination is powered exclusively by the {@code since} parameter.
     *
     * @param request pagination token
     * @return first HTTP page of users
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a page or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> list(final GitHubPageRequest request) throws IOException {
        return list(USERS, Map.of(), request, GitHubListSpec.since(USERS_TYPE));
    }

    /**
     * Get a user ({@code GET /users/{username}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param login GitHub username
     * @return the user, or empty if GitHub has no such user
     * @throws IllegalArgumentException if {@code login} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubUserView> getByLogin(final String login) throws IOException {
        return caller.fetchOrEmpty(get(USER, login), GitHubUserView.class);
    }

    /**
     * Get contextual information for a user ({@code GET /users/{username}/hovercard}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param username GitHub username
     * @return the hovercard, or empty if GitHub has no such user
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubUserContext> getUserContext(final String username) throws IOException {
        return getUserContext(username, GitHubGetUserContextFilter.empty());
    }

    /**
     * Get contextual information for a user ({@code GET /users/{username}/hovercard}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param username GitHub username
     * @param filter hovercard query filters
     * @return the hovercard, or empty if GitHub has no such user
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubUserContext> getUserContext(final String username, final GitHubGetUserContextFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return fetchUserContextWithQuery(username, filter.toQueryParameters());
    }

    private Optional<GitHubUserContext> fetchUserContextWithQuery(
            final String username, final Map<String, String> query) throws IOException {

        return caller.fetchOrEmpty(get(USER_HOVERCARD, username).withQuery(query), GitHubUserContext.class);
    }
}
