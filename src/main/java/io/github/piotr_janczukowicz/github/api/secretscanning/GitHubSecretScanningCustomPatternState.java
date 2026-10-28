package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Secret scanning custom pattern state. */
@CatalogSchema({
    "components/schemas/secret-scanning-custom-pattern/properties/state",
    "components/parameters/secret-scanning-custom-pattern-state/schema"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSecretScanningCustomPatternState {
    PUBLISHED("published"),
    UNPUBLISHED("unpublished");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternState> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningCustomPatternState.class, GitHubSecretScanningCustomPatternState::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternState(final String wireValue) {
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
     * Resolves secret scanning custom pattern state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning custom pattern state
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub secret scanning custom pattern state: " + value));
    }
}
