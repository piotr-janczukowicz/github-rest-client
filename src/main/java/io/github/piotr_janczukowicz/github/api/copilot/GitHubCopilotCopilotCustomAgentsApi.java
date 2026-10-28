package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;

/** GitHub Copilot custom agents REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotCustomAgentsApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_CUSTOM_AGENTS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/custom-agents");
    private static final PathTemplate ENTERPRISE_CUSTOM_AGENTS_SOURCE =
            PathTemplate.of("/enterprises/{enterprise}/copilot/custom-agents/source");

    /**
     * Copilot custom agents resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotCustomAgentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List custom agents of an enterprise ({@code GET /enterprises/{enterprise}/copilot/custom-agents}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listCustomAgentsForEnterprise(String,
     * GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of custom agents
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCopilotCustomAgent> listCustomAgentsForEnterprise(final String enterprise)
            throws IOException {
        return listCustomAgentsForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List custom agents of an enterprise ({@code GET /enterprises/{enterprise}/copilot/custom-agents}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return requested page of custom agents, empty when no source repository is configured
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCopilotCustomAgent> listCustomAgentsForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_CUSTOM_AGENTS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubGetCopilotCustomAgentsForEnterpriseResponse.class,
                        GitHubGetCopilotCustomAgentsForEnterpriseResponse::customAgents,
                        null),
                enterprise);
    }

    /**
     * Get the custom agents source repository of an enterprise ({@code GET
     * /enterprises/{enterprise}/copilot/custom-agents/source}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return source organization and repository
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse getCustomAgentsSourceForEnterprise(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_CUSTOM_AGENTS_SOURCE, enterprise),
                GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse.class);
    }

    /**
     * Set the custom agents source organization of an enterprise ({@code PUT
     * /enterprises/{enterprise}/copilot/custom-agents/source}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload source organization to set
     * @return source organization, repository and ruleset
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse updateCustomAgentsSourceForEnterprise(
            final String enterprise, final GitHubSetCopilotCustomAgentsSourceForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENTERPRISE_CUSTOM_AGENTS_SOURCE, enterprise), payload),
                GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse.class);
    }

    /**
     * Delete the custom agents source of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/copilot/custom-agents/source}).
     *
     * @param enterprise the slug version of the enterprise name
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteCustomAgentsSourceForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(ENTERPRISE_CUSTOM_AGENTS_SOURCE, enterprise));
    }
}
