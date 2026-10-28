package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enterprise-access status to filter the export by. */
@CatalogSchema(
        "operations/enterprise-admin/create-enterprise-token-inventory-export/requestBody/properties/authorization_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateEnterpriseTokenInventoryExportAuthorizationState {
    /** Currently authorized. */
    CURRENTLY_AUTHORIZED("currently_authorized"),
    /** Member owned only. */
    MEMBER_OWNED_ONLY("member_owned_only");

    private static final EnumUtils.WireIndex<GitHubCreateEnterpriseTokenInventoryExportAuthorizationState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.class,
                    GitHubCreateEnterpriseTokenInventoryExportAuthorizationState::wireValue);

    private final String wireValue;

    GitHubCreateEnterpriseTokenInventoryExportAuthorizationState(final String wireValue) {
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
    public static GitHubCreateEnterpriseTokenInventoryExportAuthorizationState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create enterprise token inventory export authorization state: " + value));
    }
}
