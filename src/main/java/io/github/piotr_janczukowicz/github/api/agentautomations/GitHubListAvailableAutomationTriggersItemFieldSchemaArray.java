package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The array variant of the schema.
 *
 * @param type Type
 * @param items Items
 * @param minItems The minimum accepted number of array items.
 * @param maxItems The maximum accepted number of array items.
 * @param uniqueItems Whether duplicate array items are rejected.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/1")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationTriggersItemFieldSchemaArray(
        GitHubListAvailableAutomationTriggersItemFieldSchemaArrayType type,
        GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems items,
        @JsonProperty("minItems") @Nullable Integer minItems,
        @JsonProperty("maxItems") @Nullable Integer maxItems,
        @JsonProperty("uniqueItems") @Nullable Boolean uniqueItems)
        implements GitHubListAvailableAutomationTriggersItemFieldSchema {
    public GitHubListAvailableAutomationTriggersItemFieldSchemaArray {
        requireNonNull(type, "type");
        requireNonNull(items, "items");
        requireAtLeastIfPresent(minItems, 0, "minItems");
        requireAtLeastIfPresent(maxItems, 0, "maxItems");
    }
}
