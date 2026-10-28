package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Threat model to be used for code scanning analysis. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/threat_model")
public enum GitHubCodeScanningDefaultSetupThreatModel {
    REMOTE("remote"),
    REMOTE_AND_LOCAL("remote_and_local"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupThreatModel> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupThreatModel.class, GitHubCodeScanningDefaultSetupThreatModel::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupThreatModel(final String wireValue) {
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
     * Resolves code scanning default setup threat model from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupThreatModel
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupThreatModel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
