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

/** GitHub user GPG keys REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersGpgKeysApi extends GitHubResourceApi {

    private static final PathTemplate GPG_KEY = PathTemplate.of("/user/gpg_keys/{gpg_key_id}");
    private static final PathTemplate USER_GPG_KEYS = PathTemplate.of("/user/gpg_keys");
    private static final PathTemplate USERS_GPG_KEYS = PathTemplate.of("/users/{username}/gpg_keys");

    private static final JsonType<List<GitHubGpgKey>> GPG_KEYS_TYPE = JsonTypes.listOf(GitHubGpgKey.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersGpgKeysApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List GPG keys for the authenticated user ({@code GET /user/gpg_keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listGpgKeys(GitHubPageRequest)}.
     *
     * @return first HTTP page of GPG keys
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGpgKey> listGpgKeys() throws IOException {
        return listGpgKeys(GitHubPageRequest.byPage());
    }

    /**
     * List GPG keys for the authenticated user ({@code GET /user/gpg_keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of GPG keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGpgKey> listGpgKeys(final GitHubPageRequest request) throws IOException {
        return pageList(USER_GPG_KEYS, Map.of(), request, GPG_KEYS_TYPE);
    }

    /**
     * List GPG keys for a user ({@code GET /users/{username}/gpg_keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listGpgKeys(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of GPG keys
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGpgKey> listGpgKeys(final String username) throws IOException {
        return listGpgKeys(username, GitHubPageRequest.byPage());
    }

    /**
     * List GPG keys for a user ({@code GET /users/{username}/gpg_keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of GPG keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGpgKey> listGpgKeys(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_GPG_KEYS, Map.of(), request, GPG_KEYS_TYPE, username);
    }

    /**
     * Create a GPG key for the authenticated user ({@code POST /user/gpg_keys}).
     *
     * @param payload GPG key to create
     * @return the created GPG key
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGpgKey createGpgKey(final GitHubCreateGpgKeyPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_GPG_KEYS), payload), GitHubGpgKey.class);
    }

    /**
     * Get a GPG key for the authenticated user ({@code GET /user/gpg_keys/{gpg_key_id}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param keyId GPG key id
     * @return the GPG key, or empty if GitHub has no such key
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubGpgKey> getGpgKey(final long keyId) throws IOException {
        return caller.fetchOrEmpty(get(GPG_KEY, keyId), GitHubGpgKey.class);
    }

    /**
     * Delete a GPG key for the authenticated user ({@code DELETE /user/gpg_keys/{gpg_key_id}}).
     *
     * @param keyId GPG key id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteGpgKey(final long keyId) throws IOException {
        caller.send(delete(GPG_KEY, keyId));
    }
}
