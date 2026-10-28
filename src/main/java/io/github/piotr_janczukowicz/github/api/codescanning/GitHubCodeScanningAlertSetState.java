package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sets the state of the code scanning alert. */
@CatalogSchema("components/schemas/code-scanning-alert-set-state")
public enum GitHubCodeScanningAlertSetState {
    OPEN("open"),
    DISMISSED("dismissed");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertSetState> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningAlertSetState.class, GitHubCodeScanningAlertSetState::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertSetState(final String wireValue) {
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
     * Resolves code scanning alert set state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertSetState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning alert set state
     */
    @JsonCreator
    public static GitHubCodeScanningAlertSetState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub code scanning alert set state: " + value));
    }
}
