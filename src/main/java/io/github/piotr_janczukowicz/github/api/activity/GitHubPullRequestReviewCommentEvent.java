package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Pull request review comment event payload. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-comment-event")
@RecordBuilder
public record GitHubPullRequestReviewCommentEvent(
        String action,
        GitHubPullRequestEventPullRequest pullRequest,
        GitHubPullRequestReviewCommentEventComment comment)
        implements GitHubEventPayload {
    public GitHubPullRequestReviewCommentEvent {
        requireNonNull(action, "action");
        requireNonNull(pullRequest, "pullRequest");
        requireNonNull(comment, "comment");
    }
}
