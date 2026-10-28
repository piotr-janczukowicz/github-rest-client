package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Private registry authentication type. */
@CatalogSchema({
    "components/schemas/org-private-registry-configuration/properties/auth_type",
    "components/schemas/org-private-registry-configuration-with-selected-repositories/properties/auth_type",
    "operations/private-registries/create-org-private-registry/requestBody/properties/auth_type",
    "operations/private-registries/update-org-private-registry/requestBody/properties/auth_type"
})
public enum GitHubPrivateRegistryAuthType {
    TOKEN("token"),
    USERNAME_PASSWORD("username_password"),
    OIDC_AZURE("oidc_azure"),
    OIDC_AWS("oidc_aws"),
    OIDC_JFROG("oidc_jfrog"),
    OIDC_CLOUDSMITH("oidc_cloudsmith"),
    OIDC_GCP("oidc_gcp"),
    ANONYMOUS("anonymous");

    private static final EnumUtils.WireIndex<GitHubPrivateRegistryAuthType> WIRE_INDEX =
            EnumUtils.index(GitHubPrivateRegistryAuthType.class, GitHubPrivateRegistryAuthType::wireValue);

    private final String wireValue;

    GitHubPrivateRegistryAuthType(final String wireValue) {
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
     * Authentication type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubPrivateRegistryAuthType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub private registry auth type: " + value));
    }
}
