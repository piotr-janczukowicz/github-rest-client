package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The items variant of the array.
 *
 * @param type Type
 * @param enumValues The allowed string item values.
 * @param minLength The minimum accepted string item length.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/1/properties/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems(
        GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsType type,
        @JsonProperty("enum") @Nullable List<String> enumValues,
        @JsonProperty("minLength") @Nullable Integer minLength) {
    public GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems {
        requireNonNull(type, "type");
        requireAtLeastIfPresent(minLength, 0, "minLength");
    }
}
