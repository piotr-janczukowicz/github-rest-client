package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Runner type to be used. */
@CatalogSchema("components/schemas/code-scanning-default-setup-update/properties/runner_type")
public enum GitHubCodeScanningDefaultSetupUpdateRunnerType {
    STANDARD("standard"),
    LABELED("labeled");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupUpdateRunnerType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningDefaultSetupUpdateRunnerType.class,
                    GitHubCodeScanningDefaultSetupUpdateRunnerType::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupUpdateRunnerType(final String wireValue) {
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
     * Resolves code scanning default setup update runner type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupUpdateRunnerType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup update runner type
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupUpdateRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning default setup update runner type: " + value));
    }
}
