package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Status. */
@CatalogSchema("components/schemas/security-and-analysis/properties/advanced_security/properties/status")
public enum GitHubSecurityAndAnalysisEnablement {
    ENABLED("enabled"),
    DISABLED("disabled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecurityAndAnalysisEnablement> WIRE_INDEX =
            EnumUtils.index(GitHubSecurityAndAnalysisEnablement.class, GitHubSecurityAndAnalysisEnablement::wireValue);

    private final String wireValue;

    GitHubSecurityAndAnalysisEnablement(final String wireValue) {
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
     * Status from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubSecurityAndAnalysisEnablement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
