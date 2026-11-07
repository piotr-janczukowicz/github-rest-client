package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Copilot team customizations REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubCopilotTeamCustomizationsApi extends GitHubResourceApi {

    private static final PathTemplate TEAM_COPILOT_CUSTOMIZATIONS =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/copilot/customizations");

    /**
     * Copilot team customizations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotTeamCustomizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get Copilot customizations for a team ({@code GET /orgs/{org}/teams/{team_slug}/copilot/customizations}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @return the team's customization document, or empty when the team has none or does not exist
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetCopilotTeamCustomizationsResponse> getCustomizations(
            final String org, final String teamSlug) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(TEAM_COPILOT_CUSTOMIZATIONS, org, teamSlug), GitHubGetCopilotTeamCustomizationsResponse.class);
    }

    /**
     * Set Copilot customizations for a team ({@code PUT /orgs/{org}/teams/{team_slug}/copilot/customizations}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @param payload customization document to set
     * @return the team's customization document after the update
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSetCopilotTeamCustomizationsResponse setCustomizations(
            final String org, final String teamSlug, final GitHubSetCopilotTeamCustomizationsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(TEAM_COPILOT_CUSTOMIZATIONS, org, teamSlug), payload),
                GitHubSetCopilotTeamCustomizationsResponse.class);
    }

    /**
     * Delete Copilot customizations for a team ({@code DELETE /orgs/{org}/teams/{team_slug}/copilot/customizations}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCustomizations(final String org, final String teamSlug) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(TEAM_COPILOT_CUSTOMIZATIONS, org, teamSlug));
    }
}
