package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Copilot content exclusion management REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotContentExclusionManagementApi extends GitHubResourceApi {

    private static final PathTemplate ORG_CONTENT_EXCLUSION = PathTemplate.of("/orgs/{org}/copilot/content_exclusion");
    private static final PathTemplate ENTERPRISE_CONTENT_EXCLUSION =
            PathTemplate.of("/enterprises/{enterprise}/copilot/content_exclusion");

    /**
     * Copilot content exclusion management resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotContentExclusionManagementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get Copilot content exclusion rules for an organization ({@code GET /orgs/{org}/copilot/content_exclusion}).
     *
     * @param org organization name
     * @return content exclusion details
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotOrganizationContentExclusionDetails getForOrganization(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CONTENT_EXCLUSION, org), GitHubCopilotOrganizationContentExclusionDetails.class);
    }

    /**
     * Set Copilot content exclusion rules for an organization ({@code PUT /orgs/{org}/copilot/content_exclusion}).
     *
     * @param org organization name
     * @param payload content exclusion rules to set
     * @return response message
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSetCopilotContentExclusionForOrganizationResponse updateForOrganization(
            final String org, final GitHubSetCopilotContentExclusionForOrganizationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ORG_CONTENT_EXCLUSION, org), payload),
                GitHubSetCopilotContentExclusionForOrganizationResponse.class);
    }

    /**
     * Get Copilot content exclusion rules for an enterprise ({@code GET
     * /enterprises/{enterprise}/copilot/content_exclusion}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return content exclusion rules keyed by repository name
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCopilotEnterpriseContentExclusionDetails getForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_CONTENT_EXCLUSION, enterprise), GitHubCopilotEnterpriseContentExclusionDetails.class);
    }

    /**
     * Set Copilot content exclusion rules for an enterprise ({@code PUT
     * /enterprises/{enterprise}/copilot/content_exclusion}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload content exclusion rules to set
     * @return response message
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubSetCopilotContentExclusionForEnterpriseResponse updateForEnterprise(
            final String enterprise, final GitHubSetCopilotContentExclusionForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENTERPRISE_CONTENT_EXCLUSION, enterprise), payload),
                GitHubSetCopilotContentExclusionForEnterpriseResponse.class);
    }
}
