package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Role for a new organization invitation. */
@CatalogSchema("operations/orgs/create-invitation/requestBody/properties/role")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrganizationInvitationRole {
    ADMIN("admin"),
    DIRECT_MEMBER("direct_member"),
    BILLING_MANAGER("billing_manager"),
    REINSTATE("reinstate");

    private static final EnumUtils.WireIndex<GitHubOrganizationInvitationRole> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationInvitationRole.class, GitHubOrganizationInvitationRole::wireValue);

    private final String wireValue;

    GitHubOrganizationInvitationRole(final String wireValue) {
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
     * Role from a wire value.
     *
     * @param value JSON value
     * @return matching role
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known role
     */
    @JsonCreator
    public static GitHubOrganizationInvitationRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization invitation role: " + value));
    }
}
