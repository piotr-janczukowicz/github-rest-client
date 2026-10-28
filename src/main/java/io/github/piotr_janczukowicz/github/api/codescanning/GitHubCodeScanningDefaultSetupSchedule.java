package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The frequency of the periodic analysis. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/schedule")
public enum GitHubCodeScanningDefaultSetupSchedule {
    WEEKLY("weekly");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupSchedule> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupSchedule.class, GitHubCodeScanningDefaultSetupSchedule::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupSchedule(final String wireValue) {
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
     * Resolves code scanning default setup schedule from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupSchedule
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup schedule
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupSchedule fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub code scanning default setup schedule: " + value));
    }
}
