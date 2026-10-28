package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The desired state of code scanning default setup. */
@CatalogSchema("components/schemas/code-scanning-default-setup-update/properties/state")
public enum GitHubCodeScanningDefaultSetupUpdateState {
    CONFIGURED("configured"),
    NOT_CONFIGURED("not-configured");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupUpdateState> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupUpdateState.class, GitHubCodeScanningDefaultSetupUpdateState::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupUpdateState(final String wireValue) {
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
     * Resolves code scanning default setup update state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupUpdateState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup update state
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning default setup update state: " + value));
    }
}
