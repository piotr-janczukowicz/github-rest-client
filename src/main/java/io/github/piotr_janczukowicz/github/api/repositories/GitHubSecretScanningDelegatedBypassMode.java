package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Secret scanning delegated bypass reviewer mode. */
@CatalogSchema(
        "components/schemas/security-and-analysis/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items/properties/mode")
public enum GitHubSecretScanningDelegatedBypassMode {
    ALWAYS("ALWAYS"),
    EXEMPT("EXEMPT"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningDelegatedBypassMode> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningDelegatedBypassMode.class, GitHubSecretScanningDelegatedBypassMode::wireValue);

    private final String wireValue;

    GitHubSecretScanningDelegatedBypassMode(final String wireValue) {
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
     * Secret scanning delegated bypass reviewer mode from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubSecretScanningDelegatedBypassMode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
