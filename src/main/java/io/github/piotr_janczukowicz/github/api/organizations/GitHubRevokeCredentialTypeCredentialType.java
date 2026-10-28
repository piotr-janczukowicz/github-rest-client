package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Credential type to revoke. */
@CatalogSchema("operations/orgs/revoke-credential-type/requestBody/properties/credential_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubRevokeCredentialTypeCredentialType {
    /** Classic pat. */
    CLASSIC_PAT("classic_pat"),
    /** Fine grained pat. */
    FINE_GRAINED_PAT("fine_grained_pat"),
    /** Ssh key. */
    SSH_KEY("ssh_key"),
    /** Oauth app token. */
    OAUTH_APP_TOKEN("oauth_app_token");

    private static final EnumUtils.WireIndex<GitHubRevokeCredentialTypeCredentialType> WIRE_INDEX = EnumUtils.index(
            GitHubRevokeCredentialTypeCredentialType.class, GitHubRevokeCredentialTypeCredentialType::wireValue);

    private final String wireValue;

    GitHubRevokeCredentialTypeCredentialType(final String wireValue) {
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
    public static GitHubRevokeCredentialTypeCredentialType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub revoke credential type credential type: " + value));
    }
}
