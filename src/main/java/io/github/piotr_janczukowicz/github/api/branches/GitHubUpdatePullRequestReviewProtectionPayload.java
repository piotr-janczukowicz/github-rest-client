package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update pull request review protection request body.
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
@CatalogSchema("operations/repos/update-pull-request-review-protection/requestBody")
public record GitHubUpdatePullRequestReviewProtectionPayload(
        @Nullable GitHubUpdateBranchProtectionDismissalRestrictions dismissalRestrictions,
        @Nullable Boolean dismissStaleReviews,
        @Nullable Boolean requireCodeOwnerReviews,
        @Nullable Integer requiredApprovingReviewCount,
        @Nullable Boolean requireLastPushApproval,
        @Nullable GitHubUpdateBranchProtectionBypassPullRequestAllowances bypassPullRequestAllowances) {}
