package io.github.piotr_janczukowicz.github.api.copilot;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Copilot cloud agent management REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotCloudAgentManagementApi extends GitHubResourceApi {

    private static final PathTemplate REPO_COPILOT_CLOUD_AGENT_CONFIGURATION =
            PathTemplate.of("/repos/{owner}/{repo}/copilot/cloud-agent/configuration");

    /**
     * Copilot cloud agent management resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotCloudAgentManagementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the Copilot cloud agent configuration for a repository ({@code GET
     * /repos/{owner}/{repo}/copilot/cloud-agent/configuration}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return cloud agent configuration
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubGetCopilotCloudAgentConfigurationResponse getConfiguration(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(REPO_COPILOT_CLOUD_AGENT_CONFIGURATION, owner, repo),
                GitHubGetCopilotCloudAgentConfigurationResponse.class);
    }
}
