package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Push protection query parameter for listing secret scanning custom patterns. */
@CatalogSchema("components/parameters/secret-scanning-custom-pattern-push-protection/schema")
public enum GitHubSecretScanningCustomPatternPushProtection {
    ENABLED("enabled"),
    DISABLED("disabled");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternPushProtection> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningCustomPatternPushProtection.class,
                    GitHubSecretScanningCustomPatternPushProtection::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternPushProtection(final String wireValue) {
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
     * Resolves secret scanning custom pattern push protection from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternPushProtection
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning custom pattern push protection
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternPushProtection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning custom pattern push protection: " + value));
    }
}
