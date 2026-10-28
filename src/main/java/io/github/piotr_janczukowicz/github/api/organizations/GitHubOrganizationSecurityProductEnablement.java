package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Organization security product enablement path parameter.
 *
 * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible repositories in
 *     an organization is closing down. Prefer code security configurations. Deprecation date {@code 2024-07-22};
 *     removal date {@code 2025-07-22}.
 */
@Deprecated(since = "2024-07-22")
@CatalogSchema("components/parameters/org-security-product-enablement/schema")
public enum GitHubOrganizationSecurityProductEnablement {
    ENABLE_ALL("enable_all"),
    DISABLE_ALL("disable_all");

    private static final EnumUtils.WireIndex<GitHubOrganizationSecurityProductEnablement> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationSecurityProductEnablement.class, GitHubOrganizationSecurityProductEnablement::wireValue);

    private final String wireValue;

    GitHubOrganizationSecurityProductEnablement(final String wireValue) {
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
     * Security product enablement from a wire value.
     *
     * @param value path parameter value
     * @return matching enablement action
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known enablement action
     */
    @JsonCreator
    public static GitHubOrganizationSecurityProductEnablement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization security product enablement: " + value));
    }
}
