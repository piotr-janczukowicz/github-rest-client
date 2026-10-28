package io.github.piotr_janczukowicz.github.api.users;

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
import java.util.Map;

/** GitHub user blocking REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersBlockingApi extends GitHubResourceApi {

    private static final PathTemplate BLOCK = PathTemplate.of("/user/blocks/{username}");
    private static final PathTemplate BLOCKS = PathTemplate.of("/user/blocks");

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersBlockingApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List users blocked by the authenticated user ({@code GET /user/blocks}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listBlocked(GitHubPageRequest)}.
     *
     * @return first HTTP page of blocked users
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSimpleUser> listBlocked() throws IOException {
        return listBlocked(GitHubPageRequest.byPage());
    }

    /**
     * List users blocked by the authenticated user ({@code GET /user/blocks}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of blocked users
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSimpleUser> listBlocked(final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return pageList(BLOCKS, Map.of(), request, USERS_TYPE);
    }

    /**
     * Check if a user is blocked by the authenticated user ({@code GET /user/blocks/{username}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param username GitHub username
     * @return whether the user is blocked
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public boolean isBlocked(final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.isExisting(get(BLOCK, username));
    }

    /**
     * Block a user ({@code PUT /user/blocks/{username}}).
     *
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void block(final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(BLOCK, username));
    }

    /**
     * Unblock a user ({@code DELETE /user/blocks/{username}}).
     *
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void unblock(final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(BLOCK, username));
    }
}
