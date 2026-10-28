package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** `pending` files have not yet been processed, while `complete` means results from the SARIF have been stored. */
@CatalogSchema("components/schemas/code-scanning-sarifs-status/properties/processing_status")
public enum GitHubCodeScanningSarifsStatusProcessingStatus {
    PENDING("pending"),
    COMPLETE("complete"),
    FAILED("failed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningSarifsStatusProcessingStatus> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningSarifsStatusProcessingStatus.class,
                    GitHubCodeScanningSarifsStatusProcessingStatus::wireValue);

    private final String wireValue;

    GitHubCodeScanningSarifsStatusProcessingStatus(final String wireValue) {
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
     * Resolves code scanning sarifs status processing status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningSarifsStatusProcessingStatus
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningSarifsStatusProcessingStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
