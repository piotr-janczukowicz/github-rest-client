package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Enterprise Cloud Copilot AI controls REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminAiControlsApi extends GitHubResourceApi {

    private static final PathTemplate APPS_MODEL_POLICIES =
            PathTemplate.of("/enterprises/{enterprise}/copilot/apps/model-policies");
    private static final PathTemplate MODEL_POLICIES =
            PathTemplate.of("/enterprises/{enterprise}/copilot/model-policies");
    private static final PathTemplate TEAM_MODEL_POLICIES =
            PathTemplate.of("/enterprises/{enterprise}/teams/{team_id}/copilot/model-policies");

    /**
     * AI controls resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminAiControlsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the model policies of the enterprise apps ({@code GET
     * /enterprises/{enterprise}/copilot/apps/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return model policies of the enterprise apps
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetEnterpriseAppsModelPoliciesResponse getEnterpriseAppsModelPolicies(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(APPS_MODEL_POLICIES, enterprise), GitHubGetEnterpriseAppsModelPoliciesResponse.class);
    }

    /**
     * Update the model policies of the enterprise apps ({@code PATCH
     * /enterprises/{enterprise}/copilot/apps/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the model policies to update
     * @return model policies of the enterprise apps after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateEnterpriseAppsModelPoliciesResponse updateEnterpriseAppsModelPolicies(
            final String enterprise, final GitHubUpdateEnterpriseAppsModelPoliciesPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(APPS_MODEL_POLICIES, enterprise), payload),
                GitHubUpdateEnterpriseAppsModelPoliciesResponse.class);
    }

    /**
     * Get the Copilot model policies of an enterprise ({@code GET /enterprises/{enterprise}/copilot/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return model policies of the enterprise
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetEnterpriseModelPoliciesResponse getEnterpriseModelPolicies(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(MODEL_POLICIES, enterprise), GitHubGetEnterpriseModelPoliciesResponse.class);
    }

    /**
     * Update the Copilot model policies of an enterprise ({@code PATCH
     * /enterprises/{enterprise}/copilot/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the default availability and model policies to update
     * @return model policies of the enterprise after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateEnterpriseModelPoliciesResponse updateEnterpriseModelPolicies(
            final String enterprise, final GitHubUpdateEnterpriseModelPoliciesPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(MODEL_POLICIES, enterprise), payload),
                GitHubUpdateEnterpriseModelPoliciesResponse.class);
    }

    /**
     * Get the Copilot model policies of an enterprise team ({@code GET
     * /enterprises/{enterprise}/teams/{team_id}/copilot/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamId the unique identifier of the enterprise team
     * @return model policies of the enterprise team, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetEnterpriseTeamModelPoliciesResponse> getEnterpriseTeamModelPolicies(
            final String enterprise, final long teamId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(TEAM_MODEL_POLICIES, enterprise, teamId), GitHubGetEnterpriseTeamModelPoliciesResponse.class);
    }

    /**
     * Update the Copilot model policies of an enterprise team ({@code PATCH
     * /enterprises/{enterprise}/teams/{team_id}/copilot/model-policies}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamId the unique identifier of the enterprise team
     * @param payload the model policies to update
     * @return model policies of the enterprise team after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateEnterpriseTeamModelPoliciesResponse updateEnterpriseTeamModelPolicies(
            final String enterprise, final long teamId, final GitHubUpdateEnterpriseTeamModelPoliciesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(TEAM_MODEL_POLICIES, enterprise, teamId), payload),
                GitHubUpdateEnterpriseTeamModelPoliciesResponse.class);
    }
}
