package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Action to take for a security feature of an enterprise. */
@CatalogSchema("operations/secret-scanning/post-security-product-enablement-for-enterprise/parameters/2")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubEnterpriseSecurityProductEnablement {
    /** Enable all. */
    ENABLE_ALL("enable_all"),
    /** Disable all. */
    DISABLE_ALL("disable_all");

    private static final EnumUtils.WireIndex<GitHubEnterpriseSecurityProductEnablement> WIRE_INDEX = EnumUtils.index(
            GitHubEnterpriseSecurityProductEnablement.class, GitHubEnterpriseSecurityProductEnablement::wireValue);

    private final String wireValue;

    GitHubEnterpriseSecurityProductEnablement(final String wireValue) {
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
    public static GitHubEnterpriseSecurityProductEnablement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub enterprise security product enablement: " + value));
    }
}
