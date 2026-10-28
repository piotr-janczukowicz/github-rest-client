package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** required_status_checks rule. */
@CatalogSchema("components/schemas/repository-rule-required-status-checks")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredStatusChecks(
        GitHubRepositoryRuleRequiredStatusChecksType type,
        @Nullable GitHubRepositoryRuleRequiredStatusChecksParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleRequiredStatusChecks {
        requireNonNull(type, "type");
    }
}
