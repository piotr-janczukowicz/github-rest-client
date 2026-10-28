package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of credential owner. */
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/owner_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTokenInventoryItemOwnerType {
    /** User. */
    USER("user"),
    /** Oauth application. */
    OAUTH_APPLICATION("oauth_application"),
    /** Github app. */
    GITHUB_APP("github_app"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTokenInventoryItemOwnerType> WIRE_INDEX = EnumUtils.index(
            GitHubEnterpriseTokenInventoryItemOwnerType.class, GitHubEnterpriseTokenInventoryItemOwnerType::wireValue);

    private final String wireValue;

    GitHubEnterpriseTokenInventoryItemOwnerType(final String wireValue) {
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
    public static GitHubEnterpriseTokenInventoryItemOwnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
