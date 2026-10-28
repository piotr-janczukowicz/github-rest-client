package io.github.piotr_janczukowicz.github.api.credentials;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub credentials REST resource for API version {@code 2026-03-10}. */
public final class GitHubCredentialsApi extends GitHubResourceApi {

    private static final PathTemplate REVOKE = PathTemplate.of("/credentials/revoke");

    /**
     * Credentials resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCredentialsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Revoke a list of credentials ({@code POST /credentials/revoke}).
     *
     * <p>Intended for unauthenticated use: GitHub answers authenticated requests with HTTP 403. Call it on a client
     * built with {@code AuthenticationProviders.none()}.
     *
     * @param payload credentials to revoke, up to 1000 per request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void revoke(final GitHubRevokePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(REVOKE), payload));
    }
}
