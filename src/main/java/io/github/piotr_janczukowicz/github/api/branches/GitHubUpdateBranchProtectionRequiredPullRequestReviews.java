package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Required pull request reviews for branch protection updates.
 *
 * @param dismissalRestrictions Dismissal restrictions
 * @param dismissStaleReviews Dismiss stale reviews
 * @param requireCodeOwnerReviews Require code owner reviews
 * @param requiredApprovingReviewCount Required approving review count
 * @param requireLastPushApproval Require last push approval
 * @param bypassPullRequestAllowances Bypass pull request allowances
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-branch-protection/requestBody/properties/required_pull_request_reviews")
public record GitHubUpdateBranchProtectionRequiredPullRequestReviews(
        @Nullable GitHubUpdateBranchProtectionDismissalRestrictions dismissalRestrictions,
        @Nullable Boolean dismissStaleReviews,
        @Nullable Boolean requireCodeOwnerReviews,
        @Nullable Integer requiredApprovingReviewCount,
        @Nullable Boolean requireLastPushApproval,
        @Nullable GitHubUpdateBranchProtectionBypassPullRequestAllowances bypassPullRequestAllowances) {}
