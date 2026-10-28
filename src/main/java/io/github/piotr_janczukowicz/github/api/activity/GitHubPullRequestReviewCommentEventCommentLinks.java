package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Comment links. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-comment-event/properties/comment/properties/_links")
@RecordBuilder
public record GitHubPullRequestReviewCommentEventCommentLinks(
        GitHubPullRequestReviewCommentEventCommentLinksSelf self,
        GitHubPullRequestReviewCommentEventCommentLinksHtml html,
        GitHubPullRequestReviewCommentEventCommentLinksPullRequest pullRequest) {
    public GitHubPullRequestReviewCommentEventCommentLinks {
        requireNonNull(self, "self");
        requireNonNull(html, "html");
        requireNonNull(pullRequest, "pullRequest");
    }
}
