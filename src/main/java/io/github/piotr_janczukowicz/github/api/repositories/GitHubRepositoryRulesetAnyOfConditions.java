package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Repository ruleset conditions ({@code repository-ruleset-conditions} or {@code org-ruleset-conditions}). */
@CatalogSchema("components/schemas/repository-ruleset/properties/conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetAnyOfConditions(
        @Nullable GitHubRepositoryRulesetConditionsRefName refName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty) {}
