package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import org.jspecify.annotations.Nullable;

/**
 * Accepted asynchronous merge request details.
 *
 * @param message Result message
 * @param uuid Merge request UUID
 * @param mergeMethod Merge method for the request
 * @param mergeAction Merge action for the request
 * @param expectedHeadSha Expected head SHA for the merge
 * @param bypassRules Whether the asynchronous merge request will attempt to bypass repository rules that the
 *     authenticated actor is permitted to bypass
 */
public record GitHubPullRequestMergeAsyncResultDetailsAccepted(
        String message,
        String uuid,
        GitHubPullRequestMergeAsyncMethod mergeMethod,
        GitHubPullRequestMergeAsyncAction mergeAction,
        String expectedHeadSha,
        @Nullable Boolean bypassRules)
        implements GitHubPullRequestMergeAsyncResultDetails {

    public GitHubPullRequestMergeAsyncResultDetailsAccepted {
        requireNonNull(message, "message");
        requireNonNull(uuid, "uuid");
        requireNonNull(mergeMethod, "mergeMethod");
        requireNonNull(mergeAction, "mergeAction");
        requireNonNull(expectedHeadSha, "expectedHeadSha");
    }
}
