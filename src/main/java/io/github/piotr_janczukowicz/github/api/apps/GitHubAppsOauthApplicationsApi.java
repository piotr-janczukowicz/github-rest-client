package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/**
 * GitHub OAuth applications authorizations REST resource for API version {@code 2026-03-10}.
 *
 * <p>The spec says that authentication works differently with the token check ({@code POST
 * /applications/{client_id}/token}) and does not name the scheme for these calls; they take the access token in the
 * request body, and an invalid token answers HTTP 404. A client that uses an unsuitable credential gets a plain HTTP
 * 401, 403 or 404.
 */
public final class GitHubAppsOauthApplicationsApi extends GitHubResourceApi {

    private static final PathTemplate GRANT = PathTemplate.of("/applications/{client_id}/grant");
    private static final PathTemplate TOKEN = PathTemplate.of("/applications/{client_id}/token");

    /**
     * OAuth applications resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppsOauthApplicationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Delete an app authorization ({@code DELETE /applications/{client_id}/grant}).
     *
     * @param clientId OAuth client id
     * @param payload access token to revoke
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteAuthorization(final String clientId, final GitHubDeleteAuthorizationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(GRANT, clientId), payload));
    }

    /**
     * Check a token ({@code POST /applications/{client_id}/token}).
     *
     * @param clientId OAuth client id
     * @param payload access token to check
     * @return authorization of the token
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthorization checkToken(final String clientId, final GitHubCheckTokenPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(TOKEN, clientId), payload), GitHubAuthorization.class);
    }

    /**
     * Reset a token ({@code PATCH /applications/{client_id}/token}).
     *
     * @param clientId OAuth client id
     * @param payload access token to reset
     * @return reset authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthorization resetToken(final String clientId, final GitHubResetTokenPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(TOKEN, clientId), payload), GitHubAuthorization.class);
    }

    /**
     * Delete an app token ({@code DELETE /applications/{client_id}/token}).
     *
     * @param clientId OAuth client id
     * @param payload access token to delete
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteToken(final String clientId, final GitHubDeleteTokenPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(TOKEN, clientId), payload));
    }
}
