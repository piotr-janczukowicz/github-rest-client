package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** tag_name_pattern rule. */
@CatalogSchema("components/schemas/repository-rule-tag-name-pattern")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleTagNamePattern(
        GitHubRepositoryRuleTagNamePatternType type,
        @Nullable GitHubRepositoryRuleTagNamePatternParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleTagNamePattern {
        requireNonNull(type, "type");
    }
}
