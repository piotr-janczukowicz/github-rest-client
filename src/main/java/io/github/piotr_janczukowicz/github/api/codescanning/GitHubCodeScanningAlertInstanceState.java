package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of a code scanning alert instance. */
@CatalogSchema("components/schemas/code-scanning-alert-instance-state")
public enum GitHubCodeScanningAlertInstanceState {
    OPEN("open"),
    FIXED("fixed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertInstanceState> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningAlertInstanceState.class, GitHubCodeScanningAlertInstanceState::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertInstanceState(final String wireValue) {
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
     * Resolves code scanning alert instance state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertInstanceState
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningAlertInstanceState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
