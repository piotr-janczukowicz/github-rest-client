package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of advisory identifier. */
@CatalogSchema("components/schemas/dependabot-alert-security-advisory/properties/identifiers/items/properties/type")
public enum GitHubDependabotAlertSecurityAdvisoryIdentifierType {
    CVE("CVE"),
    GHSA("GHSA"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertSecurityAdvisoryIdentifierType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubDependabotAlertSecurityAdvisoryIdentifierType.class,
                    GitHubDependabotAlertSecurityAdvisoryIdentifierType::wireValue);

    private final String wireValue;

    GitHubDependabotAlertSecurityAdvisoryIdentifierType(final String wireValue) {
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
     * Resolves Dependabot alert security advisory identifier type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertSecurityAdvisoryIdentifierType
     * @throws NullPointerException if {@code value} is {@code null} type
     */
    @JsonCreator
    public static GitHubDependabotAlertSecurityAdvisoryIdentifierType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
