package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

/**
 * Message-only asynchronous merge result details.
 *
 * @param message Result message
 */
public record GitHubPullRequestMergeAsyncResultDetailsMessage(String message)
        implements GitHubPullRequestMergeAsyncResultDetails {

    public GitHubPullRequestMergeAsyncResultDetailsMessage {
        requireNonNull(message, "message");
    }
}
