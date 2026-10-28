package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** non_fast_forward rule. */
@CatalogSchema("components/schemas/repository-rule-non-fast-forward")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleNonFastForward(GitHubRepositoryRuleNonFastForwardType type)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleNonFastForward {
        requireNonNull(type, "type");
    }
}
