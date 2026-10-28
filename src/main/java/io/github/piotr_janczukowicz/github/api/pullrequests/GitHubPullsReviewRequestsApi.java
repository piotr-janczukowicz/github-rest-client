package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import org.jspecify.annotations.Nullable;

/** GitHub pull request review requests REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsReviewRequestsApi extends GitHubResourceApi {

    private static final PathTemplate PULL_REQUESTED_REVIEWERS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers");
    private static final PathTemplate PULL_REREQUEST_REVIEWERS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers/rerequest");

    /**
     * Review requests resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsReviewRequestsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List requested reviewers ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return users and teams with pending review requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReviewRequest list(final String owner, final String repo, final int pullNumber)
            throws IOException {

        return caller.fetch(
                get(PULL_REQUESTED_REVIEWERS, owner, repo, pullNumber), GitHubPullRequestReviewRequest.class);
    }

    /**
     * Request reviewers ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional reviewers to request
     * @return updated pull request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestSimple request(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubRequestReviewersPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(post(PULL_REQUESTED_REVIEWERS, owner, repo, pullNumber), payload),
                GitHubPullRequestSimple.class);
    }

    /**
     * Remove requested reviewers ({@code DELETE /repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload reviewers to remove
     * @return updated pull request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestSimple remove(
            final String owner, final String repo, final int pullNumber, final GitHubRemoveReviewersPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(PULL_REQUESTED_REVIEWERS, owner, repo, pullNumber), payload),
                GitHubPullRequestSimple.class);
    }

    /**
     * Rerequest reviewers ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/requested_reviewers/rerequest}).
     *
     * <p>Rerequests reviews for a pull request from a given set of users and/or teams.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional reviewers to rerequest
     * @return updated pull request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestSimple rerequest(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubRerequestReviewersPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                caller.jsonIfPresent(post(PULL_REREQUEST_REVIEWERS, owner, repo, pullNumber), payload),
                GitHubPullRequestSimple.class);
    }
}
