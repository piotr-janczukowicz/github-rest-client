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

/** GitHub user SSH signing keys REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersSshSigningKeysApi extends GitHubResourceApi {

    private static final PathTemplate SSH_SIGNING_KEY = PathTemplate.of("/user/ssh_signing_keys/{ssh_signing_key_id}");
    private static final PathTemplate USER_SSH_SIGNING_KEYS = PathTemplate.of("/user/ssh_signing_keys");
    private static final PathTemplate USERS_SSH_SIGNING_KEYS = PathTemplate.of("/users/{username}/ssh_signing_keys");

    private static final JsonType<List<GitHubSshSigningKey>> SSH_SIGNING_KEYS_TYPE =
            JsonTypes.listOf(GitHubSshSigningKey.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersSshSigningKeysApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List SSH signing keys for the authenticated user ({@code GET /user/ssh_signing_keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSshSigningKeys(GitHubPageRequest)}.
     *
     * @return first HTTP page of SSH signing keys
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSshSigningKey> listSshSigningKeys() throws IOException {
        return listSshSigningKeys(GitHubPageRequest.byPage());
    }

    /**
     * List SSH signing keys for the authenticated user ({@code GET /user/ssh_signing_keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of SSH signing keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSshSigningKey> listSshSigningKeys(final GitHubPageRequest request) throws IOException {
        return pageList(USER_SSH_SIGNING_KEYS, Map.of(), request, SSH_SIGNING_KEYS_TYPE);
    }

    /**
     * List SSH signing keys for a user ({@code GET /users/{username}/ssh_signing_keys}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSshSigningKeys(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of SSH signing keys
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSshSigningKey> listSshSigningKeys(final String username) throws IOException {
        return listSshSigningKeys(username, GitHubPageRequest.byPage());
    }

    /**
     * List SSH signing keys for a user ({@code GET /users/{username}/ssh_signing_keys}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of SSH signing keys
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSshSigningKey> listSshSigningKeys(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_SSH_SIGNING_KEYS, Map.of(), request, SSH_SIGNING_KEYS_TYPE, username);
    }

    /**
     * Create a SSH signing key for the authenticated user ({@code POST /user/ssh_signing_keys}).
     *
     * @param payload SSH signing key to create
     * @return the created SSH signing key
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSshSigningKey createSshSigningKey(final GitHubCreateSshSigningKeyPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_SSH_SIGNING_KEYS), payload), GitHubSshSigningKey.class);
    }

    /**
     * Get an SSH signing key for the authenticated user ({@code GET /user/ssh_signing_keys/{ssh_signing_key_id}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param keyId SSH signing key id
     * @return the SSH signing key, or empty if GitHub has no such key
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubSshSigningKey> getSshSigningKey(final long keyId) throws IOException {
        return caller.fetchOrEmpty(get(SSH_SIGNING_KEY, keyId), GitHubSshSigningKey.class);
    }

    /**
     * Delete an SSH signing key for the authenticated user ({@code DELETE
     * /user/ssh_signing_keys/{ssh_signing_key_id}}).
     *
     * @param keyId SSH signing key id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteSshSigningKey(final long keyId) throws IOException {
        caller.send(delete(SSH_SIGNING_KEY, keyId));
    }
}
