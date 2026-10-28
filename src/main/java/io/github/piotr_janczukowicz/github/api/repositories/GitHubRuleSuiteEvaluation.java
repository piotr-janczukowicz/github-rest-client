package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Evaluation of a single rule within a rule suite: its source, enforcement, result and details. */
@CatalogSchema("components/schemas/rule-suite/properties/rule_evaluations/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRuleSuiteEvaluation(
        @Nullable GitHubRuleSuiteRuleSource ruleSource,
        @Nullable GitHubRuleSuiteRuleEvaluationEnforcement enforcement,
        @Nullable GitHubRuleSuiteRuleEvaluationResult result,
        @Nullable String ruleType,
        @Nullable GitHubRuleSuiteMetadata metadata,
        @Nullable OptionalNull<String> details) {}
