package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pending organization invitation role filter. */
@CatalogSchema("operations/orgs/list-pending-invitations/parameters/3")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrganizationPendingInvitationRole {
    ALL("all"),
    ADMIN("admin"),
    DIRECT_MEMBER("direct_member"),
    BILLING_MANAGER("billing_manager"),
    HIRING_MANAGER("hiring_manager");

    private static final EnumUtils.WireIndex<GitHubOrganizationPendingInvitationRole> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPendingInvitationRole.class, GitHubOrganizationPendingInvitationRole::wireValue);

    private final String wireValue;

    GitHubOrganizationPendingInvitationRole(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Role filter from a wire value.
     *
     * @param value query parameter value
     * @return matching role filter
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known role filter
     */
    @JsonCreator
    public static GitHubOrganizationPendingInvitationRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization pending invitation role filter: " + value));
    }
}
