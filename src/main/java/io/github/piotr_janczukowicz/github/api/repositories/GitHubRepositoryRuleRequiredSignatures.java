package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** required_signatures rule. */
@CatalogSchema("components/schemas/repository-rule-required-signatures")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredSignatures(GitHubRepositoryRuleRequiredSignaturesType type)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleRequiredSignatures {
        requireNonNull(type, "type");
    }
}
