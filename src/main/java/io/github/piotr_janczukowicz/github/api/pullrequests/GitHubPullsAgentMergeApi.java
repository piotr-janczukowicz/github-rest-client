package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub pull request Agent Merge REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsAgentMergeApi extends GitHubResourceApi {

    private static final PathTemplate AGENT_MERGE =
            PathTemplate.of("/agents/repos/{owner}/{repo}/pulls/{pull_number}/agent-merge");

    /**
     * Agent Merge resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsAgentMergeApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the Agent Merge configuration of a pull request ({@code GET
     * /agents/repos/{owner}/{repo}/pulls/{pull_number}/agent-merge}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return Agent Merge configuration, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetAgentMergeEnrollmentResponse> getConfiguration(
            final String owner, final String repo, final int pullNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(AGENT_MERGE, owner, repo, pullNumber), GitHubGetAgentMergeEnrollmentResponse.class);
    }

    /**
     * Enable or update Agent Merge for a pull request ({@code PUT
     * /agents/repos/{owner}/{repo}/pulls/{pull_number}/agent-merge}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload actions Copilot may perform on the pull request
     * @return the Agent Merge configuration after the change (HTTP 200 when updated, 201 when created)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPutAgentMergeEnrollmentResponse putConfiguration(
            final String owner,
            final String repo,
            final int pullNumber,
            final GitHubPutAgentMergeEnrollmentPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(AGENT_MERGE, owner, repo, pullNumber), payload),
                GitHubPutAgentMergeEnrollmentResponse.class);
    }

    /**
     * Disable Agent Merge for a pull request ({@code DELETE
     * /agents/repos/{owner}/{repo}/pulls/{pull_number}/agent-merge}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteConfiguration(final String owner, final String repo, final int pullNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(AGENT_MERGE, owner, repo, pullNumber));
    }
}
