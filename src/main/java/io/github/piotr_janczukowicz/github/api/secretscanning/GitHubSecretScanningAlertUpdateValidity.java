package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sets the validity of the secret scanning alert. Can be `active`, `inactive`, or `null` to clear the override. */
@CatalogSchema("operations/secret-scanning/update-alert/requestBody/properties/validity")
public enum GitHubSecretScanningAlertUpdateValidity {
    ACTIVE("active"),
    INACTIVE("inactive");

    private static final EnumUtils.WireIndex<GitHubSecretScanningAlertUpdateValidity> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningAlertUpdateValidity.class, GitHubSecretScanningAlertUpdateValidity::wireValue);

    private final String wireValue;

    GitHubSecretScanningAlertUpdateValidity(final String wireValue) {
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
     * Resolves secret scanning alert update validity from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningAlertUpdateValidity
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning alert update validity
     */
    @JsonCreator
    public static GitHubSecretScanningAlertUpdateValidity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub secret scanning alert update validity: " + value));
    }
}
