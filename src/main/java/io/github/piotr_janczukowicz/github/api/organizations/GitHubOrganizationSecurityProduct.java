package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Organization security product path parameter.
 *
 * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible repositories in
 *     an organization is closing down. Prefer code security configurations. Deprecation date {@code 2024-07-22};
 *     removal date {@code 2025-07-22}.
 */
@Deprecated(since = "2024-07-22")
@CatalogSchema("components/parameters/security-product/schema")
public enum GitHubOrganizationSecurityProduct {
    DEPENDENCY_GRAPH("dependency_graph"),
    DEPENDABOT_ALERTS("dependabot_alerts"),
    DEPENDABOT_SECURITY_UPDATES("dependabot_security_updates"),
    ADVANCED_SECURITY("advanced_security"),
    CODE_SCANNING_DEFAULT_SETUP("code_scanning_default_setup"),
    SECRET_SCANNING("secret_scanning"),
    SECRET_SCANNING_PUSH_PROTECTION("secret_scanning_push_protection");

    private static final EnumUtils.WireIndex<GitHubOrganizationSecurityProduct> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationSecurityProduct.class, GitHubOrganizationSecurityProduct::wireValue);

    private final String wireValue;

    GitHubOrganizationSecurityProduct(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in path parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Security product from a wire value.
     *
     * @param value path parameter value
     * @return matching security product
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known security product
     */
    @JsonCreator
    public static GitHubOrganizationSecurityProduct fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization security product: " + value));
    }
}
