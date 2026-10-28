package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Repository ruleset conditions for ref names. */
@CatalogSchema("components/schemas/repository-ruleset-conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditions(@Nullable GitHubRepositoryRulesetConditionsRefName refName) {}
