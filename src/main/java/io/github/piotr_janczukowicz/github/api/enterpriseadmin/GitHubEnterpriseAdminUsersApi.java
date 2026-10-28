package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.apps.GitHubAuthorization;
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

/** GitHub Enterprise Server user administration REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminUsersApi extends GitHubResourceApi {

    private static final PathTemplate KEYS = PathTemplate.of("/admin/keys");
    private static final PathTemplate KEY = PathTemplate.of("/admin/keys/{key_ids}");
    private static final PathTemplate TOKENS = PathTemplate.of("/admin/tokens");
    private static final PathTemplate TOKEN = PathTemplate.of("/admin/tokens/{token_id}");
    private static final PathTemplate USERS = PathTemplate.of("/admin/users");
    private static final PathTemplate USER = PathTemplate.of("/admin/users/{username}");
    private static final PathTemplate AUTHORIZATIONS = PathTemplate.of("/admin/users/{username}/authorizations");
    private static final PathTemplate SITE_ADMIN = PathTemplate.of("/users/{username}/site_admin");
    private static final PathTemplate SUSPENDED = PathTemplate.of("/users/{username}/suspended");

    private static final JsonType<List<GitHubPublicKeyFull>> KEYS_TYPE = JsonTypes.listOf(GitHubPublicKeyFull.class);
    private static final JsonType<List<GitHubAuthorization>> TOKENS_TYPE = JsonTypes.listOf(GitHubAuthorization.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminUsersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List public keys ({@code GET /admin/keys}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPublicKeyFull> listPublicKeys() throws IOException {
        return listPublicKeys(GitHubListPublicKeysFilter.empty());
    }

    /**
     * List public keys ({@code GET /admin/keys}).
     *
     * @param filter sort and direction
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPublicKeyFull> listPublicKeys(final GitHubListPublicKeysFilter filter) throws IOException {
        return listPublicKeys(filter, GitHubPageRequest.byPage());
    }

    /**
     * List public keys ({@code GET /admin/keys}).
     *
     * @param filter sort and direction
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPublicKeyFull> listPublicKeys(
            final GitHubListPublicKeysFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(KEYS, filter.toQueryParameters(), request, KEYS_TYPE);
    }

    /**
     * Delete a public key ({@code DELETE /admin/keys/{key_ids}}).
     *
     * @param keyIds key id
     * @throws IllegalArgumentException if {@code keyIds} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deletePublicKey(final String keyIds) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(KEY, keyIds));
    }

    /**
     * List personal access tokens ({@code GET /admin/tokens}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubAuthorization> listPersonalAccessTokens() throws IOException {
        return listPersonalAccessTokens(GitHubPageRequest.byPage());
    }

    /**
     * List personal access tokens ({@code GET /admin/tokens}).
     *
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubAuthorization> listPersonalAccessTokens(final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TOKENS, Map.of(), request, TOKENS_TYPE);
    }

    /**
     * Delete a personal access token ({@code DELETE /admin/tokens/{token_id}}).
     *
     * @param tokenId token id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deletePersonalAccessToken(final long tokenId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(TOKEN, tokenId));
    }

    /**
     * Create a user ({@code POST /admin/users}).
     *
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubSimpleUser createUser(final GitHubCreateUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USERS), payload), GitHubSimpleUser.class);
    }

    /**
     * Update the username of a user ({@code PATCH /admin/users/{username}}).
     *
     * @param username user login
     * @param payload request body
     * @return the resource
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubUpdateUsernameForUserResponse updateUsernameForUser(
            final String username, final GitHubUpdateUsernameForUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(USER, username), payload), GitHubUpdateUsernameForUserResponse.class);
    }

    /**
     * Delete a user ({@code DELETE /admin/users/{username}}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deleteUser(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(USER, username));
    }

    /**
     * Create an impersonation OAuth token ({@code POST /admin/users/{username}/authorizations}).
     *
     * @param username user login
     * @param payload request body
     * @return the resource
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization createImpersonationOAuthToken(
            final String username, final GitHubCreateImpersonationOAuthTokenPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(AUTHORIZATIONS, username), payload), GitHubAuthorization.class);
    }

    /**
     * Delete an impersonation OAuth token ({@code DELETE /admin/users/{username}/authorizations}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deleteImpersonationOAuthToken(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(AUTHORIZATIONS, username));
    }

    /**
     * Promote a user to be a site administrator ({@code PUT /users/{username}/site_admin}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void promoteUserToBeSiteAdministrator(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(put(SITE_ADMIN, username));
    }

    /**
     * Demote a site administrator ({@code DELETE /users/{username}/site_admin}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void demoteSiteAdministrator(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(SITE_ADMIN, username));
    }

    /**
     * Suspend a user ({@code PUT /users/{username}/suspended}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void suspendUser(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(put(SUSPENDED, username));
    }

    /**
     * Suspend a user ({@code PUT /users/{username}/suspended}).
     *
     * @param username user login
     * @param payload request body
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void suspendUser(final String username, final GitHubSuspendUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(SUSPENDED, username), payload));
    }

    /**
     * Unsuspend a user ({@code DELETE /users/{username}/suspended}).
     *
     * @param username user login
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void unsuspendUser(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(SUSPENDED, username));
    }

    /**
     * Unsuspend a user ({@code DELETE /users/{username}/suspended}).
     *
     * @param username user login
     * @param payload request body
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void unsuspendUser(final String username, final GitHubUnsuspendUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(SUSPENDED, username), payload));
    }
}
