package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The security product to get GitHub Advanced Security active committers for. */
@CatalogSchema("operations/billing/get-github-advanced-security-billing-ghe/parameters/1")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubAdvancedSecurityProduct {
    /** Code security. */
    CODE_SECURITY("code_security"),
    /** Secret protection. */
    SECRET_PROTECTION("secret_protection");

    private static final EnumUtils.WireIndex<GitHubAdvancedSecurityProduct> WIRE_INDEX =
            EnumUtils.index(GitHubAdvancedSecurityProduct.class, GitHubAdvancedSecurityProduct::wireValue);

    private final String wireValue;

    GitHubAdvancedSecurityProduct(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAdvancedSecurityProduct fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub advanced security product: " + value));
    }
}
