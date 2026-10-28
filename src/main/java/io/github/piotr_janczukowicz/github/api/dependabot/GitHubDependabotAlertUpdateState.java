package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the Dependabot alert. */
@CatalogSchema("operations/dependabot/update-alert/requestBody/properties/state")
public enum GitHubDependabotAlertUpdateState {
    DISMISSED("dismissed"),
    OPEN("open");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertUpdateState> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotAlertUpdateState.class, GitHubDependabotAlertUpdateState::wireValue);

    private final String wireValue;

    GitHubDependabotAlertUpdateState(final String wireValue) {
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
     * Resolves Dependabot alert update state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertUpdateState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert update state
     */
    @JsonCreator
    public static GitHubDependabotAlertUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Dependabot alert update state: " + value));
    }
}
