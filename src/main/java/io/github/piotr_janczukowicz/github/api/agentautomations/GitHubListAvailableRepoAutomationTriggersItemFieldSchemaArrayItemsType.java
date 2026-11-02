package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type value. */
@CatalogSchema(
        "operations/agent-automations/list-available-repo-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/1/properties/items/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType {
    STRING("string");

    private static final EnumUtils.WireIndex<GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType>
            WIRE_INDEX = EnumUtils.index(
                    GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType.class,
                    GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType::wireValue);

    private final String wireValue;

    GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType(final String wireValue) {
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
    public static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list available automation triggers item field schema array items type: "
                                + value));
    }
}
