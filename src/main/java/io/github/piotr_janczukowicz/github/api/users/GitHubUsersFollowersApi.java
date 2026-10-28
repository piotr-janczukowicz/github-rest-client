package io.github.piotr_janczukowicz.github.api.users;

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

/** GitHub user followers REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersFollowersApi extends GitHubResourceApi {

    private static final PathTemplate USER_FOLLOWED_USER = PathTemplate.of("/user/following/{username}");
    private static final PathTemplate USER_FOLLOWING = PathTemplate.of("/user/following");
    private static final PathTemplate USER_FOLLOWERS = PathTemplate.of("/user/followers");
    private static final PathTemplate USERS_FOLLOWED_USER =
            PathTemplate.of("/users/{username}/following/{target_user}");
    private static final PathTemplate USERS_FOLLOWING = PathTemplate.of("/users/{username}/following");
    private static final PathTemplate USERS_FOLLOWERS = PathTemplate.of("/users/{username}/followers");

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersFollowersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List followers of the authenticated user ({@code GET /user/followers}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listFollowers(GitHubPageRequest)}.
     *
     * @return first HTTP page of followers
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowers() throws IOException {
        return listFollowers(GitHubPageRequest.byPage());
    }

    /**
     * List followers of the authenticated user ({@code GET /user/followers}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of followers
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowers(final GitHubPageRequest request) throws IOException {
        return pageList(USER_FOLLOWERS, Map.of(), request, USERS_TYPE);
    }

    /**
     * List followers of a user ({@code GET /users/{username}/followers}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listFollowers(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of followers
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowers(final String username) throws IOException {
        return listFollowers(username, GitHubPageRequest.byPage());
    }

    /**
     * List followers of a user ({@code GET /users/{username}/followers}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of followers
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowers(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_FOLLOWERS, Map.of(), request, USERS_TYPE, username);
    }

    /**
     * List the people the authenticated user follows ({@code GET /user/following}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listFollowing(GitHubPageRequest)}.
     *
     * @return first HTTP page of followed users
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowing() throws IOException {
        return listFollowing(GitHubPageRequest.byPage());
    }

    /**
     * List the people the authenticated user follows ({@code GET /user/following}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of followed users
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowing(final GitHubPageRequest request) throws IOException {
        return pageList(USER_FOLLOWING, Map.of(), request, USERS_TYPE);
    }

    /**
     * List the people a user follows ({@code GET /users/{username}/following}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listFollowing(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of followed users
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowing(final String username) throws IOException {
        return listFollowing(username, GitHubPageRequest.byPage());
    }

    /**
     * List the people a user follows ({@code GET /users/{username}/following}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of followed users
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listFollowing(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_FOLLOWING, Map.of(), request, USERS_TYPE, username);
    }

    /**
     * Check if a person is followed by the authenticated user ({@code GET /user/following/{username}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param username GitHub username
     * @return whether the authenticated user follows {@code username}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean isFollowing(final String username) throws IOException {
        return caller.isExisting(get(USER_FOLLOWED_USER, username));
    }

    /**
     * Check if a user follows another user ({@code GET /users/{username}/following/{target_user}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param username GitHub username
     * @param targetUser target GitHub username
     * @return whether {@code username} follows {@code targetUser}
     * @throws IllegalArgumentException if {@code username} or {@code targetUser} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean isFollowing(final String username, final String targetUser) throws IOException {
        return caller.isExisting(get(USERS_FOLLOWED_USER, username, targetUser));
    }

    /**
     * Follow a user ({@code PUT /user/following/{username}}).
     *
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void follow(final String username) throws IOException {
        caller.send(put(USER_FOLLOWED_USER, username));
    }

    /**
     * Unfollow a user ({@code DELETE /user/following/{username}}).
     *
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unfollow(final String username) throws IOException {
        caller.send(delete(USER_FOLLOWED_USER, username));
    }
}
