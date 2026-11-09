package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Trigger field schema array items type. Shared by the 2 schemas that have this shape:
 * git_hub_list_available_repo_automation_triggers_item_field_schema_array_items_type,
 * git_hub_list_available_repo_automation_triggers_item_field_schema_string_type.
 */
@CatalogSchema({
    "operations/agent-automations/list-available-repo-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/1/properties/items/properties/type",
    "operations/agent-automations/list-available-repo-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/0/properties/type"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationTriggerFieldSchemaArrayItemsType {
    STRING("string");

    private static final EnumUtils.WireIndex<GitHubAutomationTriggerFieldSchemaArrayItemsType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubAutomationTriggerFieldSchemaArrayItemsType.class,
                    GitHubAutomationTriggerFieldSchemaArrayItemsType::wireValue);

    private final String wireValue;

    GitHubAutomationTriggerFieldSchemaArrayItemsType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAutomationTriggerFieldSchemaArrayItemsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list available automation triggers item field schema array items type: "
                                + value));
    }
}
