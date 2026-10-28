package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** RequiredReviewerConfiguration. */
@CatalogSchema("components/schemas/repository-rule-params-required-reviewer-configuration")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsRequiredReviewerConfiguration(
        List<String> filePatterns,
        int minimumApprovals,
        GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer reviewer) {
    public GitHubRepositoryRuleParamsRequiredReviewerConfiguration {
        filePatterns = List.copyOf(requireNonNull(filePatterns, "filePatterns"));
        requireNonNull(reviewer, "reviewer");
    }
}
