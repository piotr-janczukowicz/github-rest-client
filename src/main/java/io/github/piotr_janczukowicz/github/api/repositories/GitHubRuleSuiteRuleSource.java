package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Source of an evaluated rule: its type, identifier and name. */
@CatalogSchema("components/schemas/rule-suite/properties/rule_evaluations/items/properties/rule_source")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRuleSuiteRuleSource(
        @Nullable String type, @Nullable Long id, @Nullable String name) {}
