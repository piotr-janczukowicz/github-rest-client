package io.github.piotr_janczukowicz.github.api.agentautomations;

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
 * The integer variant of the schema.
 *
 * @param type Type
 * @param enumValues The allowed integer values.
 * @param minimum The minimum accepted integer value.
 * @param maximum The maximum accepted integer value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/2")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationTriggersItemFieldSchemaInteger(
        GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerType type,
        @JsonProperty("enum") @Nullable List<Integer> enumValues,
        @Nullable Integer minimum,
        @Nullable Integer maximum)
        implements GitHubListAvailableAutomationTriggersItemFieldSchema {
    public GitHubListAvailableAutomationTriggersItemFieldSchemaInteger {
        requireNonNull(type, "type");
    }
}
