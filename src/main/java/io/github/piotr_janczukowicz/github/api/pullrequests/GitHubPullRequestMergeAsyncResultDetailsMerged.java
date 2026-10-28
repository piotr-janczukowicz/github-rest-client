package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

/**
 * Already-merged asynchronous merge result details.
 *
 * @param message Result message
 * @param sha Merge commit SHA
 */
public record GitHubPullRequestMergeAsyncResultDetailsMerged(String message, String sha)
        implements GitHubPullRequestMergeAsyncResultDetails {

    public GitHubPullRequestMergeAsyncResultDetailsMerged {
        requireNonNull(message, "message");
        requireNonNull(sha, "sha");
    }
}
