package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of a credential in the token inventory. */
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/credential_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTokenInventoryItemCredentialType {
    /** Classic pat. */
    CLASSIC_PAT("classic_pat"),
    /** Oauth app user token. */
    OAUTH_APP_USER_TOKEN("oauth_app_user_token"),
    /** Github app user token. */
    GITHUB_APP_USER_TOKEN("github_app_user_token"),
    /** Fine grained pat. */
    FINE_GRAINED_PAT("fine_grained_pat"),
    /** Ssh key. */
    SSH_KEY("ssh_key"),
    /** Github app installation. */
    GITHUB_APP_INSTALLATION("github_app_installation"),
    /** Federated jti. */
    FEDERATED_JTI("federated_jti"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTokenInventoryItemCredentialType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseTokenInventoryItemCredentialType.class,
                    GitHubEnterpriseTokenInventoryItemCredentialType::wireValue);

    private final String wireValue;

    GitHubEnterpriseTokenInventoryItemCredentialType(final String wireValue) {
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
    public static GitHubEnterpriseTokenInventoryItemCredentialType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
