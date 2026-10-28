package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Reviewer. */
@CatalogSchema("components/schemas/repository-rule-params-reviewer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsReviewer(long id, GitHubRepositoryRuleParamsReviewerType type) {
    public GitHubRepositoryRuleParamsReviewer {
        requireNonNull(type, "type");
    }
}
