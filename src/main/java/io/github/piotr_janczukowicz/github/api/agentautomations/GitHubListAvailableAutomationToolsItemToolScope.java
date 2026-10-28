package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The tool's behavior level. {@code read} tools only retrieve data. {@code write} tools can change data. This describes
 * tool behavior, not a specific GitHub token permission.
 */
@CatalogSchema(
        "operations/agent-automations/list-available-automation-tools/200/items/properties/tools/items/properties/scope")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAvailableAutomationToolsItemToolScope {
    READ("read"),
    WRITE("write");

    private static final EnumUtils.WireIndex<GitHubListAvailableAutomationToolsItemToolScope> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListAvailableAutomationToolsItemToolScope.class,
                    GitHubListAvailableAutomationToolsItemToolScope::wireValue);

    private final String wireValue;

    GitHubListAvailableAutomationToolsItemToolScope(final String wireValue) {
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
    public static GitHubListAvailableAutomationToolsItemToolScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list available automation tools item tool scope: " + value));
    }
}
