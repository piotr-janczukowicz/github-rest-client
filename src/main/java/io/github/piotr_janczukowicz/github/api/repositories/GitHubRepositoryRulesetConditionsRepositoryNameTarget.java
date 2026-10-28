package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Repository ruleset conditions for repository names. */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-name-target")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryNameTarget(
        GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName) {
    public GitHubRepositoryRulesetConditionsRepositoryNameTarget {
        requireNonNull(repositoryName, "repositoryName");
    }
}
