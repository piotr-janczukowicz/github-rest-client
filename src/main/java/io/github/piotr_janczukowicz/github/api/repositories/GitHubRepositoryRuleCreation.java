package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** creation rule. */
@CatalogSchema("components/schemas/repository-rule-creation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType type)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleCreation {
        requireNonNull(type, "type");
    }
}
