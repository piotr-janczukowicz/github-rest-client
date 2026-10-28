package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * CodeQL query suite for organization security product enablement.
 *
 * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible repositories in
 *     an organization is closing down. Prefer code security configurations. Deprecation date {@code 2024-07-22};
 *     removal date {@code 2025-07-22}.
 */
@Deprecated(since = "2024-07-22")
@CatalogSchema("operations/orgs/enable-or-disable-security-product-on-all-org-repos/requestBody/properties/query_suite")
public enum GitHubOrganizationSecurityProductQuerySuite {
    DEFAULT("default"),
    EXTENDED("extended");

    private static final EnumUtils.WireIndex<GitHubOrganizationSecurityProductQuerySuite> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationSecurityProductQuerySuite.class, GitHubOrganizationSecurityProductQuerySuite::wireValue);

    private final String wireValue;

    GitHubOrganizationSecurityProductQuerySuite(final String wireValue) {
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
     * Query suite from a wire value.
     *
     * @param value JSON value
     * @return matching query suite
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known query suite
     */
    @JsonCreator
    public static GitHubOrganizationSecurityProductQuerySuite fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization security product query suite: " + value));
    }
}
