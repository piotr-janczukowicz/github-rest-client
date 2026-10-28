package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Threat model to be used for code scanning analysis. */
@CatalogSchema("components/schemas/code-scanning-default-setup-update/properties/threat_model")
public enum GitHubCodeScanningDefaultSetupUpdateThreatModel {
    REMOTE("remote"),
    REMOTE_AND_LOCAL("remote_and_local");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupUpdateThreatModel> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningDefaultSetupUpdateThreatModel.class,
                    GitHubCodeScanningDefaultSetupUpdateThreatModel::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupUpdateThreatModel(final String wireValue) {
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
     * Resolves code scanning default setup update threat model from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupUpdateThreatModel
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup update threat model
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupUpdateThreatModel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning default setup update threat model: " + value));
    }
}
