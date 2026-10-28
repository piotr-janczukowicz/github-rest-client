package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code scanning default setup has been configured or not. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/state")
public enum GitHubCodeScanningDefaultSetupState {
    CONFIGURED("configured"),
    NOT_CONFIGURED("not-configured"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupState> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningDefaultSetupState.class, GitHubCodeScanningDefaultSetupState::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupState(final String wireValue) {
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
     * Resolves code scanning default setup state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupState
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
