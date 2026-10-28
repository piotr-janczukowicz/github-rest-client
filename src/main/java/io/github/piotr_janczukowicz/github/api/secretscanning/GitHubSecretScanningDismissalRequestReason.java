package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason the user provided for requesting the dismissal. */
@CatalogSchema("components/schemas/secret-scanning-dismissal-request/properties/data/items/properties/reason")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubSecretScanningDismissalRequestReason {
    /** Fixed later. */
    FIXED_LATER("fixed_later"),
    /** False positive. */
    FALSE_POSITIVE("false_positive"),
    /** Tests. */
    TESTS("tests"),
    /** Revoked. */
    REVOKED("revoked");

    private static final EnumUtils.WireIndex<GitHubSecretScanningDismissalRequestReason> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningDismissalRequestReason.class, GitHubSecretScanningDismissalRequestReason::wireValue);

    private final String wireValue;

    GitHubSecretScanningDismissalRequestReason(final String wireValue) {
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
     * Resolves the secret scanning dismissal request reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningDismissalRequestReason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning dismissal request reason
     */
    @JsonCreator
    public static GitHubSecretScanningDismissalRequestReason fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning dismissal request reason: " + value));
    }
}
