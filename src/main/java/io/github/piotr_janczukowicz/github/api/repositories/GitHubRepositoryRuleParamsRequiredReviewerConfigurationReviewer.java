package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reviewer that must approve changes to the files matched by a required reviewer configuration: its identifier and
 * type.
 */
@CatalogSchema("components/schemas/repository-rule-params-required-reviewer-configuration/properties/reviewer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer(
        long id, GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewerType type) {
    public GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer {
        requireNonNull(type, "type");
    }
}
