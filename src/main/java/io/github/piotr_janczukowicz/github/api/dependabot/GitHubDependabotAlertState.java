package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the Dependabot alert. */
@CatalogSchema({
    "components/schemas/dependabot-alert/properties/state",
    "components/schemas/dependabot-alert-with-repository/properties/state"
})
public enum GitHubDependabotAlertState {
    AUTO_DISMISSED("auto_dismissed"),
    DISMISSED("dismissed"),
    FIXED("fixed"),
    OPEN("open"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertState> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotAlertState.class, GitHubDependabotAlertState::wireValue);

    private final String wireValue;

    GitHubDependabotAlertState(final String wireValue) {
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
     * Resolves Dependabot alert state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertState
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependabotAlertState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
