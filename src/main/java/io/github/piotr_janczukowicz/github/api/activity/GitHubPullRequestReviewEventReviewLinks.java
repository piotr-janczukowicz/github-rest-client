package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Review links. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-event/properties/review/properties/_links")
@RecordBuilder
public record GitHubPullRequestReviewEventReviewLinks(
        GitHubPullRequestReviewEventReviewLinksHtml html,
        GitHubPullRequestReviewEventReviewLinksPullRequest pullRequest) {
    public GitHubPullRequestReviewEventReviewLinks {
        requireNonNull(html, "html");
        requireNonNull(pullRequest, "pullRequest");
    }
}
