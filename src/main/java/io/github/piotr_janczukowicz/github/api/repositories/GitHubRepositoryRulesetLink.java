package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Ruleset hypermedia link ({@code href} is not required by OpenAPI). */
@CatalogSchema({
    "components/schemas/repository-ruleset/properties/_links/properties/self",
    "components/schemas/repository-ruleset/properties/_links/properties/html"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetLink(@Nullable String href) {}
