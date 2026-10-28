package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Ruleset hypermedia links. */
@CatalogSchema("components/schemas/repository-ruleset/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetLinks(
        @Nullable GitHubRepositoryRulesetLink self,
        @Nullable GitHubRepositoryRulesetLink html) {}
