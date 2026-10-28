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
        "operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items/properties/schema/oneOf/0/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAvailableAutomationTriggersItemFieldSchemaStringType {
    STRING("string");

    private static final EnumUtils.WireIndex<GitHubListAvailableAutomationTriggersItemFieldSchemaStringType>
            WIRE_INDEX = EnumUtils.index(
                    GitHubListAvailableAutomationTriggersItemFieldSchemaStringType.class,
                    GitHubListAvailableAutomationTriggersItemFieldSchemaStringType::wireValue);

    private final String wireValue;

    GitHubListAvailableAutomationTriggersItemFieldSchemaStringType(final String wireValue) {
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
    public static GitHubListAvailableAutomationTriggersItemFieldSchemaStringType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list available automation triggers item field schema string type: " + value));
    }
}
