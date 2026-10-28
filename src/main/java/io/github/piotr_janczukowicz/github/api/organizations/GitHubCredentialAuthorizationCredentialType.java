package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Human-readable description of the credential type. */
@CatalogSchema("components/schemas/credential-authorization/properties/credential_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCredentialAuthorizationCredentialType {
    /** Personal access token. */
    PERSONAL_ACCESS_TOKEN("personal access token"),
    /** SSH key. */
    SSH_KEY("SSH key"),
    /** OAuth app token. */
    OAUTH_APP_TOKEN("OAuth app token"),
    /** GitHub app token. */
    GITHUB_APP_TOKEN("GitHub app token"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCredentialAuthorizationCredentialType> WIRE_INDEX = EnumUtils.index(
            GitHubCredentialAuthorizationCredentialType.class, GitHubCredentialAuthorizationCredentialType::wireValue);

    private final String wireValue;

    GitHubCredentialAuthorizationCredentialType(final String wireValue) {
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
    public static GitHubCredentialAuthorizationCredentialType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
