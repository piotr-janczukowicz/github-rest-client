package io.github.piotr_janczukowicz.github.api.users;

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
import java.util.Map;
import java.util.Optional;

/** GitHub user public SSH keys REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersKeysApi extends GitHubResourceApi {

    private static final PathTemplate KEY = PathTemplate.of("/user/keys/{key_id}");
    private static final PathTemplate USER_KEYS = PathTemplate.of("/user/keys");
    private static final PathTemplate USERS_KEYS = PathTemplate.of("/users/{username}/keys");

    private static final JsonType<List<GitHubKey>> KEYS_TYPE = JsonTypes.listOf(GitHubKey.class);
    private static final JsonType<List<GitHubKeySimple>> KEY_SIMPLE_TYPE = JsonTypes.listOf(GitHubKeySimple.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersKeysApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List public SSH keys for the authenticated user ({@code GET /user/keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPublicKeys(GitHubPageRequest)}.
     *
     * @return first HTTP page of public SSH keys
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubKey> listPublicKeys() throws IOException {
        return listPublicKeys(GitHubPageRequest.byPage());
    }

    /**
     * List public SSH keys for the authenticated user ({@code GET /user/keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of public SSH keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubKey> listPublicKeys(final GitHubPageRequest request) throws IOException {
        return pageList(USER_KEYS, Map.of(), request, KEYS_TYPE);
    }

    /**
     * List public keys for a user ({@code GET /users/{username}/keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPublicKeys(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of verified public SSH keys
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubKeySimple> listPublicKeys(final String username) throws IOException {
        return listPublicKeys(username, GitHubPageRequest.byPage());
    }

    /**
     * List public keys for a user ({@code GET /users/{username}/keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of verified public SSH keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubKeySimple> listPublicKeys(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_KEYS, Map.of(), request, KEY_SIMPLE_TYPE, username);
    }

    /**
     * Create a public SSH key for the authenticated user ({@code POST /user/keys}).
     *
     * @param payload public SSH key to create
     * @return the created public SSH key
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubKey createPublicSshKey(final GitHubCreatePublicSshKeyPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_KEYS), payload), GitHubKey.class);
    }

    /**
     * Get a public SSH key for the authenticated user ({@code GET /user/keys/{key_id}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param keyId public SSH key id
     * @return the public SSH key, or empty if GitHub has no such key
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubKey> getPublicKey(final long keyId) throws IOException {
        return caller.fetchOrEmpty(get(KEY, keyId), GitHubKey.class);
    }

    /**
     * Delete a public SSH key for the authenticated user ({@code DELETE /user/keys/{key_id}}).
     *
     * @param keyId public SSH key id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePublicKey(final long keyId) throws IOException {
        caller.send(delete(KEY, keyId));
    }
}
