package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sets the state of the secret scanning alert. You must provide `resolution` when you set the state to `resolved`. */
@CatalogSchema({
    "components/schemas/secret-scanning-alert-state",
    "components/parameters/secret-scanning-alert-state/schema"
})
public enum GitHubSecretScanningAlertState {
    OPEN("open"),
    RESOLVED("resolved");

    private static final EnumUtils.WireIndex<GitHubSecretScanningAlertState> WIRE_INDEX =
            EnumUtils.index(GitHubSecretScanningAlertState.class, GitHubSecretScanningAlertState::wireValue);

    private final String wireValue;

    GitHubSecretScanningAlertState(final String wireValue) {
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
     * Resolves secret scanning alert state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningAlertState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning alert state
     */
    @JsonCreator
    public static GitHubSecretScanningAlertState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub secret scanning alert state: " + value));
    }
}
