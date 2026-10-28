package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** A classification of the file. */
@CatalogSchema("components/schemas/code-scanning-alert-classification")
public enum GitHubCodeScanningAlertClassification {
    SOURCE("source"),
    GENERATED("generated"),
    TEST("test"),
    LIBRARY("library"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertClassification> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningAlertClassification.class, GitHubCodeScanningAlertClassification::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertClassification(final String wireValue) {
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
     * Resolves code scanning alert classification from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertClassification
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningAlertClassification fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
