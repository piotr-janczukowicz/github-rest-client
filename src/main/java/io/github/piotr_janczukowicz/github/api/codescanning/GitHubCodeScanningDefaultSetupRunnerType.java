package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Runner type to be used. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/runner_type")
public enum GitHubCodeScanningDefaultSetupRunnerType {
    STANDARD("standard"),
    LABELED("labeled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupRunnerType> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupRunnerType.class, GitHubCodeScanningDefaultSetupRunnerType::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupRunnerType(final String wireValue) {
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
     * Resolves code scanning default setup runner type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupRunnerType
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
