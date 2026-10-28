package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Actor that made a ruleset change: its identifier and type. */
@CatalogSchema("components/schemas/ruleset-version/properties/actor")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRulesetVersionActor(
        @Nullable Long id, @Nullable String type) {}
