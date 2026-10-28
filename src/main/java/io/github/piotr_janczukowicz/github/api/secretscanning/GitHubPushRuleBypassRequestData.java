package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A push rule that was violated and is requested to be bypassed.
 *
 * @param rulesetId The ID of the ruleset for the rules that were violated
 * @param rulesetName The name of the ruleset for the rules that were violated
 * @param totalViolations The number of rule violations generated from the push associated with this request
 * @param ruleType The type of rule that was violated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/push-rule-bypass-request/properties/data/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPushRuleBypassRequestData(
        @Nullable Long rulesetId,
        @Nullable String rulesetName,
        @Nullable Integer totalViolations,
        @Nullable String ruleType) {}
