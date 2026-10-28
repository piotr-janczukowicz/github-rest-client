package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Parameters of the pull request rule: required approvals, review requirements and allowed merge methods. */
@CatalogSchema("components/schemas/repository-rule-pull-request/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulePullRequestParameters(
        @Nullable List<GitHubRepositoryRulePullRequestAllowedMergeMethod> allowedMergeMethods,
        boolean dismissStaleReviewsOnPush,
        @Nullable GitHubRepositoryRuleParamsDismissalRestriction dismissalRestriction,
        boolean requireCodeOwnerReview,
        boolean requireLastPushApproval,
        int requiredApprovingReviewCount,
        boolean requiredReviewThreadResolution,
        @Nullable List<GitHubRepositoryRuleParamsRequiredReviewerConfiguration> requiredReviewers) {

    public GitHubRepositoryRulePullRequestParameters {
        if (allowedMergeMethods != null) {
            allowedMergeMethods = List.copyOf(allowedMergeMethods);
        }
        requireBetween(requiredApprovingReviewCount, 0, 10, "requiredApprovingReviewCount");
        if (requiredReviewers != null) {
            requiredReviewers = List.copyOf(requiredReviewers);
        }
    }
}
