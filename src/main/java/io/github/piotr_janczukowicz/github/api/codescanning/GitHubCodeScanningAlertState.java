package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of a code scanning alert. */
@CatalogSchema("components/schemas/code-scanning-alert-state")
public enum GitHubCodeScanningAlertState {
    OPEN("open"),
    DISMISSED("dismissed"),
    FIXED("fixed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertState> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningAlertState.class, GitHubCodeScanningAlertState::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertState(final String wireValue) {
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
     * Resolves code scanning alert state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertState
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningAlertState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
