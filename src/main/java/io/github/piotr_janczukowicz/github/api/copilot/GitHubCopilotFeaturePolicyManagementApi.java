package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Copilot feature policy management REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotFeaturePolicyManagementApi extends GitHubResourceApi {

    private static final PathTemplate ORG_FEATURE_POLICIES = PathTemplate.of("/orgs/{org}/copilot/feature-policies");

    /**
     * Copilot feature policy management resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotFeaturePolicyManagementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get feature policies of an organization ({@code GET /orgs/{org}/copilot/feature-policies}).
     *
     * @param org organization name
     * @return default availability and feature policies
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetOrganizationFeaturePoliciesResponse getOrganizationFeaturePolicies(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_FEATURE_POLICIES, org), GitHubGetOrganizationFeaturePoliciesResponse.class);
    }

    /**
     * Update feature policies of an organization ({@code PATCH /orgs/{org}/copilot/feature-policies}).
     *
     * @param org organization name
     * @param payload the default availability to update
     * @return default availability and feature policies after the update
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateOrganizationFeaturePoliciesResponse updateOrganizationFeaturePolicies(
            final String org, final GitHubUpdateOrganizationFeaturePoliciesPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_FEATURE_POLICIES, org), payload),
                GitHubUpdateOrganizationFeaturePoliciesResponse.class);
    }
}
