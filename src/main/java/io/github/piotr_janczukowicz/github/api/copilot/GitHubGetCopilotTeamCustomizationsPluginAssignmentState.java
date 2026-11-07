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
        "operations/copilot/get-copilot-team-customizations/200/properties/pluginAssignments/additionalProperties/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetCopilotTeamCustomizationsPluginAssignmentState {
    DEFAULT_ENABLED("defaultEnabled");

    private static final EnumUtils.WireIndex<GitHubGetCopilotTeamCustomizationsPluginAssignmentState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetCopilotTeamCustomizationsPluginAssignmentState.class,
                    GitHubGetCopilotTeamCustomizationsPluginAssignmentState::wireValue);

    private final String wireValue;

    GitHubGetCopilotTeamCustomizationsPluginAssignmentState(final String wireValue) {
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
    public static GitHubGetCopilotTeamCustomizationsPluginAssignmentState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub get copilot team customizations plugin assignment state: " + value));
    }
}
