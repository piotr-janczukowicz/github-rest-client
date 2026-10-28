package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Protected branch required pull request reviews.
 *
 * @param url Url
 * @param dismissalRestrictions Dismissal restrictions
 * @param dismissStaleReviews Dismiss stale reviews
 * @param requireCodeOwnerReviews Require code owner reviews
 * @param requiredApprovingReviewCount Required approving review count
 * @param requireLastPushApproval Require last push approval
 * @param bypassPullRequestAllowances Bypass pull request allowances
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch/properties/required_pull_request_reviews")
public record GitHubProtectedBranchRequiredPullRequestReviews(
        String url,
        @Nullable GitHubProtectedBranchDismissalRestrictions dismissalRestrictions,
        @Nullable Boolean dismissStaleReviews,
        @Nullable Boolean requireCodeOwnerReviews,
        @Nullable Integer requiredApprovingReviewCount,
        @Nullable Boolean requireLastPushApproval,
        @Nullable GitHubProtectedBranchBypassPullRequestAllowances bypassPullRequestAllowances) {

    public GitHubProtectedBranchRequiredPullRequestReviews {
        requireNonNull(url, "url");
    }
}
