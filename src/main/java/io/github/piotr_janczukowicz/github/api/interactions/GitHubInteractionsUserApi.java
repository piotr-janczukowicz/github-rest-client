package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub authenticated-user interaction restrictions REST resource for API version {@code 2026-03-10}. */
public final class GitHubInteractionsUserApi extends GitHubResourceApi {

    private static final PathTemplate INTERACTION_LIMITS = PathTemplate.of("/user/interaction-limits");

    /**
     * Authenticated-user interactions resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubInteractionsUserApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get interaction restrictions for your public repositories ({@code GET /user/interaction-limits}).
     *
     * @return restrictions, or empty when GitHub returns HTTP 204, no body, or an empty JSON object
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubInteractionLimitResponse> getRestrictionsForAuthenticatedUser() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchUnless(get(INTERACTION_LIMITS), GitHubInteractionLimitResponse.class, HttpStatus.NO_CONTENT);
    }

    /**
     * Set interaction restrictions for your public repositories ({@code PUT /user/interaction-limits}).
     *
     * @param payload interaction limit
     * @return applied restrictions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubInteractionLimitResponse setRestrictionsForAuthenticatedUser(final GitHubInteractionLimit payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(INTERACTION_LIMITS), payload), GitHubInteractionLimitResponse.class);
    }

    /**
     * Remove interaction restrictions from your public repositories ({@code DELETE /user/interaction-limits}).
     *
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeRestrictionsForAuthenticatedUser() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(INTERACTION_LIMITS));
    }
}
