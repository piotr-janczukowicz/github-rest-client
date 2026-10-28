package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Security feature to enable or disable for an enterprise. */
@CatalogSchema("operations/secret-scanning/post-security-product-enablement-for-enterprise/parameters/1")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubEnterpriseSecurityProduct {
    /** Advanced security. */
    ADVANCED_SECURITY("advanced_security"),
    /** Advanced security user namespace. */
    ADVANCED_SECURITY_USER_NAMESPACE("advanced_security_user_namespace"),
    /** Dependabot alerts. */
    DEPENDABOT_ALERTS("dependabot_alerts"),
    /** Secret scanning. */
    SECRET_SCANNING("secret_scanning"),
    /** Secret scanning push protection. */
    SECRET_SCANNING_PUSH_PROTECTION("secret_scanning_push_protection"),
    /** Secret scanning non provider patterns. */
    SECRET_SCANNING_NON_PROVIDER_PATTERNS("secret_scanning_non_provider_patterns");

    private static final EnumUtils.WireIndex<GitHubEnterpriseSecurityProduct> WIRE_INDEX =
            EnumUtils.index(GitHubEnterpriseSecurityProduct.class, GitHubEnterpriseSecurityProduct::wireValue);

    private final String wireValue;

    GitHubEnterpriseSecurityProduct(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubEnterpriseSecurityProduct fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub enterprise security product: " + value));
    }
}
