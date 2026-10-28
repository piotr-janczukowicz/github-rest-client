package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** committer_email_pattern rule. */
@CatalogSchema("components/schemas/repository-rule-committer-email-pattern")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCommitterEmailPattern(
        GitHubRepositoryRuleCommitterEmailPatternType type,
        @Nullable GitHubRepositoryRuleCommitterEmailPatternParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleCommitterEmailPattern {
        requireNonNull(type, "type");
    }
}
