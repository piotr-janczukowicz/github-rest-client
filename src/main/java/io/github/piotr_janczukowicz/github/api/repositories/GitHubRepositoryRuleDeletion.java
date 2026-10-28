package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** deletion rule. */
@CatalogSchema("components/schemas/repository-rule-deletion")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleDeletion(GitHubRepositoryRuleDeletionType type)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleDeletion {
        requireNonNull(type, "type");
    }
}
