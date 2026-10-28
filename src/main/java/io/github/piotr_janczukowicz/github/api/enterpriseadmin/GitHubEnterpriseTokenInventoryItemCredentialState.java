package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of a credential in the token inventory. */
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/credential_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTokenInventoryItemCredentialState {
    /** Active. */
    ACTIVE("active"),
    /** Expired. */
    EXPIRED("expired"),
    /** Revoked. */
    REVOKED("revoked"),
    /** Deleted. */
    DELETED("deleted"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTokenInventoryItemCredentialState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseTokenInventoryItemCredentialState.class,
                    GitHubEnterpriseTokenInventoryItemCredentialState::wireValue);

    private final String wireValue;

    GitHubEnterpriseTokenInventoryItemCredentialState(final String wireValue) {
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
    public static GitHubEnterpriseTokenInventoryItemCredentialState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
