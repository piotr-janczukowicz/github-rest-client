package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** required_linear_history rule. */
@CatalogSchema("components/schemas/repository-rule-required-linear-history")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredLinearHistory(GitHubRepositoryRuleRequiredLinearHistoryType type)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleRequiredLinearHistory {
        requireNonNull(type, "type");
    }
}
