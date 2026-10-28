package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** commit_author_email_pattern rule. */
@CatalogSchema("components/schemas/repository-rule-commit-author-email-pattern")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCommitAuthorEmailPattern(
        GitHubRepositoryRuleCommitAuthorEmailPatternType type,
        @Nullable GitHubRepositoryRuleCommitAuthorEmailPatternParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleCommitAuthorEmailPattern {
        requireNonNull(type, "type");
    }
}
