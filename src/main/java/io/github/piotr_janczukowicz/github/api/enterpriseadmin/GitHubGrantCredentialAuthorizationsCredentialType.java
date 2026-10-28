package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of credential to grant authorizations for. */
@CatalogSchema({
    "operations/enterprise-admin/grant-credential-authorizations/requestBody/properties/credential_type",
    "operations/enterprise-admin/grant-credential-authorizations/201/oneOf/0/properties/credential_type",
    "operations/enterprise-admin/grant-credential-authorizations/201/oneOf/1/properties/credential_type"
})
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGrantCredentialAuthorizationsCredentialType {
    /** Classic personal access token. */
    CLASSIC_PAT("classic_pat"),
    /** SSH key. */
    SSH_KEY("ssh_key");

    private static final EnumUtils.WireIndex<GitHubGrantCredentialAuthorizationsCredentialType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGrantCredentialAuthorizationsCredentialType.class,
                    GitHubGrantCredentialAuthorizationsCredentialType::wireValue);

    private final String wireValue;

    GitHubGrantCredentialAuthorizationsCredentialType(final String wireValue) {
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
    public static GitHubGrantCredentialAuthorizationsCredentialType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub grant credential authorizations credential type: " + value));
    }
}
