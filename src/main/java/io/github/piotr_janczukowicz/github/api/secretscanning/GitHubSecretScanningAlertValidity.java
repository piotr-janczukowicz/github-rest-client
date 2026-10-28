package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Secret scanning alert validity. */
@CatalogSchema({
    "components/schemas/secret-scanning-alert/properties/validity",
    "components/schemas/secret-scanning-alert-with-metadata/properties/validity",
    "components/schemas/organization-secret-scanning-alert/properties/validity"
})
public enum GitHubSecretScanningAlertValidity {
    ACTIVE("active"),
    INACTIVE("inactive"),
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningAlertValidity> WIRE_INDEX =
            EnumUtils.index(GitHubSecretScanningAlertValidity.class, GitHubSecretScanningAlertValidity::wireValue);

    private final String wireValue;

    GitHubSecretScanningAlertValidity(final String wireValue) {
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
     * Resolves secret scanning alert validity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningAlertValidity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning alert validity
     */
    @JsonCreator
    public static GitHubSecretScanningAlertValidity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub secret scanning alert validity: " + value));
    }
}
