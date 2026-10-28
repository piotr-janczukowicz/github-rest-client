package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason the bypass was requested. */
@CatalogSchema("components/schemas/secret-scanning-bypass-request/properties/data/items/properties/bypass_reason")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubSecretScanningBypassRequestBypassReason {
    /** Used in tests. */
    USED_IN_TESTS("used_in_tests"),
    /** False positive. */
    FALSE_POSITIVE("false_positive"),
    /** Fix later. */
    FIX_LATER("fix_later");

    private static final EnumUtils.WireIndex<GitHubSecretScanningBypassRequestBypassReason> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningBypassRequestBypassReason.class,
                    GitHubSecretScanningBypassRequestBypassReason::wireValue);

    private final String wireValue;

    GitHubSecretScanningBypassRequestBypassReason(final String wireValue) {
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
     * Resolves the secret scanning bypass request bypass reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningBypassRequestBypassReason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning bypass request bypass reason
     */
    @JsonCreator
    public static GitHubSecretScanningBypassRequestBypassReason fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning bypass request bypass reason: " + value));
    }
}
