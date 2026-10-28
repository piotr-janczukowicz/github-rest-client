package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetweenIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Protected branch pull request review.
 *
 * @param url Url
 * @param dismissalRestrictions Dismissal restrictions
 * @param bypassPullRequestAllowances Bypass pull request allowances
 * @param dismissStaleReviews Dismiss stale reviews
 * @param requireCodeOwnerReviews Require code owner reviews
 * @param requiredApprovingReviewCount Required approving review count
 * @param requireLastPushApproval Require last push approval
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch-pull-request-review")
public record GitHubProtectedBranchPullRequestReview(
        @Nullable String url,
        @Nullable GitHubProtectedBranchPullRequestReviewDismissalRestrictions dismissalRestrictions,
        @Nullable GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances bypassPullRequestAllowances,
        boolean dismissStaleReviews,
        boolean requireCodeOwnerReviews,
        @Nullable Integer requiredApprovingReviewCount,
        @Nullable Boolean requireLastPushApproval) {

    public GitHubProtectedBranchPullRequestReview {
        requireBetweenIfPresent(requiredApprovingReviewCount, 0, 6, "requiredApprovingReviewCount");
    }
}
