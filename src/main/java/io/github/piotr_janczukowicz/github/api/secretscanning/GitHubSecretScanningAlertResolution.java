package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** **Required when the `state` is `resolved`.** The reason for resolving the alert. */
@CatalogSchema("components/schemas/secret-scanning-alert-resolution")
public enum GitHubSecretScanningAlertResolution {
    FALSE_POSITIVE("false_positive"),
    WONT_FIX("wont_fix"),
    REVOKED("revoked"),
    USED_IN_TESTS("used_in_tests");

    private static final EnumUtils.WireIndex<GitHubSecretScanningAlertResolution> WIRE_INDEX =
            EnumUtils.index(GitHubSecretScanningAlertResolution.class, GitHubSecretScanningAlertResolution::wireValue);

    private final String wireValue;

    GitHubSecretScanningAlertResolution(final String wireValue) {
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
     * Resolves secret scanning alert resolution from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningAlertResolution
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning alert resolution
     */
    @JsonCreator
    public static GitHubSecretScanningAlertResolution fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub secret scanning alert resolution: " + value));
    }
}
