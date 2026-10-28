package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Parameters of the commit message pattern rule: the pattern, the operator used to match it and whether the match is
 * negated.
 */
@CatalogSchema("components/schemas/repository-rule-commit-message-pattern/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCommitMessagePatternParameters(
        @Nullable String name, @Nullable Boolean negate, GitHubRepositoryRulePatternOperator operator, String pattern) {
    public GitHubRepositoryRuleCommitMessagePatternParameters {
        requireNonNull(operator, "operator");
        requireNonNull(pattern, "pattern");
    }
}
