package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enablement status of GitHub Advanced Security features. */
@CatalogSchema({
    "components/schemas/code-security-configuration/properties/advanced_security",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/advanced_security",
    "operations/code-security/create-configuration/requestBody/properties/advanced_security",
    "operations/code-security/update-configuration/requestBody/properties/advanced_security",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/advanced_security"
})
public enum GitHubCodeSecurityAdvancedSecurity {
    ENABLED("enabled"),
    DISABLED("disabled"),
    CODE_SECURITY("code_security"),
    SECRET_PROTECTION("secret_protection");

    private static final EnumUtils.WireIndex<GitHubCodeSecurityAdvancedSecurity> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSecurityAdvancedSecurity.class, GitHubCodeSecurityAdvancedSecurity::wireValue);

    private final String wireValue;

    GitHubCodeSecurityAdvancedSecurity(final String wireValue) {
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
    public static GitHubCodeSecurityAdvancedSecurity fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubCodeSecurityAdvancedSecurity value: " + value));
    }
}
