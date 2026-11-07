package io.github.piotr_janczukowicz.github.api.pullrequests;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import org.jspecify.annotations.Nullable;

/** GitHub pull request Copilot code review REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsCopilotCodeReviewApi extends GitHubResourceApi {

    private static final PathTemplate PULL_COPILOT_CODE_REVIEW =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/copilot-code-review");

    /**
     * Copilot code review resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsCopilotCodeReviewApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Request a Copilot code review for a pull request ({@code POST
     * /repos/{owner}/{repo}/pulls/{pull_number}/copilot-code-review}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional review options, or {@code null} for the default review effort
     * @return the created review request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotCodeReviewRequest request(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubRequestCopilotCodeReviewPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(post(PULL_COPILOT_CODE_REVIEW, owner, repo, pullNumber), payload),
                GitHubCopilotCodeReviewRequest.class);
    }

    /**
     * Request a Copilot code review with the default review effort ({@code POST
     * /repos/{owner}/{repo}/pulls/{pull_number}/copilot-code-review}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return the created review request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotCodeReviewRequest request(final String owner, final String repo, final int pullNumber)
            throws IOException {
        return request(owner, repo, pullNumber, null);
    }
}
