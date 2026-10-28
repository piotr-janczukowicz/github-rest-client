package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enforcement status of a code security configuration. */
@CatalogSchema({
    "components/schemas/code-security-configuration/properties/enforcement",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/enforcement",
    "operations/code-security/create-configuration/requestBody/properties/enforcement",
    "operations/code-security/update-configuration/requestBody/properties/enforcement",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/enforcement"
})
public enum GitHubCodeSecurityEnforcement {
    ENFORCED("enforced"),
    UNENFORCED("unenforced"),
    ENTERPRISE_ENFORCED("enterprise_enforced");

    private static final EnumUtils.WireIndex<GitHubCodeSecurityEnforcement> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSecurityEnforcement.class, GitHubCodeSecurityEnforcement::wireValue);

    private final String wireValue;

    GitHubCodeSecurityEnforcement(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCodeSecurityEnforcement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubCodeSecurityEnforcement value: " + value));
    }
}
