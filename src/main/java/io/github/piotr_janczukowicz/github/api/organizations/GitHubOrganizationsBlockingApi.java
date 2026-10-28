package io.github.piotr_janczukowicz.github.api.organizations;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
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

/** GitHub organization blocking REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsBlockingApi extends GitHubResourceApi {

    private static final PathTemplate BLOCK = PathTemplate.of("/orgs/{org}/blocks/{username}");
    private static final PathTemplate BLOCKS = PathTemplate.of("/orgs/{org}/blocks");

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Blocking resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsBlockingApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List users blocked by an organization ({@code GET /orgs/{org}/blocks}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listBlocked(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of blocked users
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSimpleUser> listBlocked(final String org) throws IOException {
        return listBlocked(org, GitHubPageRequest.byPage());
    }

    /**
     * List users blocked by an organization ({@code GET /orgs/{org}/blocks}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of blocked users
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSimpleUser> listBlocked(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return pageList(BLOCKS, Map.of(), request, USERS_TYPE, org);
    }

    /**
     * Check if a user is blocked by an organization ({@code GET /orgs/{org}/blocks/{username}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param org organization name
     * @param username GitHub username
     * @return whether the user is blocked
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public boolean isBlocked(final String org, final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.isExisting(get(BLOCK, org, username));
    }

    /**
     * Block a user ({@code PUT /orgs/{org}/blocks/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void block(final String org, final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(BLOCK, org, username));
    }

    /**
     * Unblock a user ({@code DELETE /orgs/{org}/blocks/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void unblock(final String org, final String username) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(BLOCK, org, username));
    }
}
