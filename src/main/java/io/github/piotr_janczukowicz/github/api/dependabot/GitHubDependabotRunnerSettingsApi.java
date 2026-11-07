package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Dependabot runner settings REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubDependabotRunnerSettingsApi extends GitHubResourceApi {

    private static final PathTemplate REPOSITORY_RUNNER_SETTINGS =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/runner-settings");

    /**
     * Dependabot runner settings resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotRunnerSettingsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Gets the Dependabot runner settings of a repository ({@code GET
     * /repos/{owner}/{repo}/dependabot/runner-settings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return the runner Dependabot uses for the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubDependabotRunnerSettings getRepositoryRunnerSettings(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(REPOSITORY_RUNNER_SETTINGS, owner, repo), GitHubDependabotRunnerSettings.class);
    }

    /**
     * Sets the Dependabot runner settings of a repository ({@code PUT
     * /repos/{owner}/{repo}/dependabot/runner-settings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload runner to use
     * @return the runner Dependabot uses for the repository after the change
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubDependabotRunnerSettings setRepositoryRunnerSettings(
            final String owner, final String repo, final GitHubSetRepositoryRunnerSettingsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(REPOSITORY_RUNNER_SETTINGS, owner, repo), payload),
                GitHubDependabotRunnerSettings.class);
    }
}
