package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enterprise-access status to filter the token inventory by. */
@CatalogSchema("operations/enterprise-admin/list-enterprise-token-inventory/parameters/4")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubListEnterpriseTokenInventoryAuthorizationState {
    /** Currently authorized. */
    CURRENTLY_AUTHORIZED("currently_authorized"),
    /** Member owned only. */
    MEMBER_OWNED_ONLY("member_owned_only");

    private static final EnumUtils.WireIndex<GitHubListEnterpriseTokenInventoryAuthorizationState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListEnterpriseTokenInventoryAuthorizationState.class,
                    GitHubListEnterpriseTokenInventoryAuthorizationState::wireValue);

    private final String wireValue;

    GitHubListEnterpriseTokenInventoryAuthorizationState(final String wireValue) {
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
    public static GitHubListEnterpriseTokenInventoryAuthorizationState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list enterprise token inventory authorization state: " + value));
    }
}
