package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The assignment state. */
@CatalogSchema(
        "operations/copilot/set-copilot-team-customizations/requestBody/properties/pluginAssignments/additionalProperties/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSetCopilotTeamCustomizationsPluginAssignmentState {
    DEFAULT_ENABLED("defaultEnabled");

    private static final EnumUtils.WireIndex<GitHubSetCopilotTeamCustomizationsPluginAssignmentState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSetCopilotTeamCustomizationsPluginAssignmentState.class,
                    GitHubSetCopilotTeamCustomizationsPluginAssignmentState::wireValue);

    private final String wireValue;

    GitHubSetCopilotTeamCustomizationsPluginAssignmentState(final String wireValue) {
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
    public static GitHubSetCopilotTeamCustomizationsPluginAssignmentState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub set copilot team customizations plugin assignment state: " + value));
    }
}
