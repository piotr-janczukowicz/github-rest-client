package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of identifier. */
@CatalogSchema({
    "components/schemas/global-advisory/properties/identifiers/items/properties/type",
    "components/schemas/repository-advisory/properties/identifiers/items/properties/type"
})
public enum GitHubAdvisoryIdentifierType {
    CVE("CVE"),
    GHSA("GHSA"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubAdvisoryIdentifierType> WIRE_INDEX =
            EnumUtils.index(GitHubAdvisoryIdentifierType.class, GitHubAdvisoryIdentifierType::wireValue);

    private final String wireValue;

    GitHubAdvisoryIdentifierType(final String wireValue) {
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
     * Resolves advisory identifier type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubAdvisoryIdentifierType
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubAdvisoryIdentifierType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
