package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Repository ruleset conditions for repository IDs. */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-id-target")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryIdTarget(
        GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId) {
    public GitHubRepositoryRulesetConditionsRepositoryIdTarget {
        requireNonNull(repositoryId, "repositoryId");
    }
}
