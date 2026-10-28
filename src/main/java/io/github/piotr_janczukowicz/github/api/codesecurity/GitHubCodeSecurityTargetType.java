package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of a code security configuration. */
@CatalogSchema("components/schemas/code-security-configuration/properties/target_type")
public enum GitHubCodeSecurityTargetType {
    GLOBAL("global"),
    ORGANIZATION("organization"),
    ENTERPRISE("enterprise"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeSecurityTargetType> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSecurityTargetType.class, GitHubCodeSecurityTargetType::wireValue);

    private final String wireValue;

    GitHubCodeSecurityTargetType(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeSecurityTargetType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
