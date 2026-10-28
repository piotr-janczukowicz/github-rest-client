package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** branch_name_pattern rule. */
@CatalogSchema("components/schemas/repository-rule-branch-name-pattern")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleBranchNamePattern(
        GitHubRepositoryRuleBranchNamePatternType type,
        @Nullable GitHubRepositoryRuleBranchNamePatternParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleBranchNamePattern {
        requireNonNull(type, "type");
    }
}
