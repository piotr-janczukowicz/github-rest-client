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

/** GitHub organization interaction restrictions REST resource for API version {@code 2026-03-10}. */
public final class GitHubInteractionsOrgsApi extends GitHubResourceApi {

    private static final PathTemplate PULLS_CREATION_CAP =
            PathTemplate.of("/orgs/{org}/interaction-limits/pulls/creation-cap");
    private static final PathTemplate INTERACTION_LIMITS = PathTemplate.of("/orgs/{org}/interaction-limits");

    /**
     * Organization interactions resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubInteractionsOrgsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get interaction restrictions for an organization ({@code GET /orgs/{org}/interaction-limits}).
     *
     * @param org organization name
     * @return restrictions, or empty when GitHub returns HTTP 204, no body, or an empty JSON object
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubInteractionLimitResponse> getRestrictionsForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchUnless(
                get(INTERACTION_LIMITS, org), GitHubInteractionLimitResponse.class, HttpStatus.NO_CONTENT);
    }

    /**
     * Set interaction restrictions for an organization ({@code PUT /orgs/{org}/interaction-limits}).
     *
     * @param org organization name
     * @param payload interaction limit
     * @return applied restrictions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubInteractionLimitResponse setRestrictionsForOrg(final String org, final GitHubInteractionLimit payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(INTERACTION_LIMITS, org), payload), GitHubInteractionLimitResponse.class);
    }

    /**
     * Remove interaction restrictions for an organization ({@code DELETE /orgs/{org}/interaction-limits}).
     *
     * @param org organization name
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeRestrictionsForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(INTERACTION_LIMITS, org));
    }

    /**
     * Get pull request creation cap for an org ({@code GET /orgs/{org}/interaction-limits/pulls/creation-cap}).
     *
     * @param org organization name
     * @return creation cap
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestCreationCap getPullRequestCreationCapForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(PULLS_CREATION_CAP, org), GitHubPullRequestCreationCap.class);
    }

    /**
     * Update pull request creation cap for an org ({@code PATCH /orgs/{org}/interaction-limits/pulls/creation-cap}).
     *
     * @param org organization name
     * @param payload creation cap update
     * @return updated creation cap
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestCreationCap updatePullRequestCreationCapForOrg(
            final String org, final GitHubUpdatePullRequestCreationCapForOrgPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(PULLS_CREATION_CAP, org), payload), GitHubPullRequestCreationCap.class);
    }
}
