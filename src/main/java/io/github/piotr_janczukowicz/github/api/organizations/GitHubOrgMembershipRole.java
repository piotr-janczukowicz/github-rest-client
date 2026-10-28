package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Role on {@code #/components/schemas/org-membership} ({@code admin}/{@code member}/{@code billing_manager}).
 *
 * <p>Distinct from {@link GitHubOrganizationMembershipRole} on {@code orgs/set-membership-for-user} request body.
 */
@CatalogSchema("components/schemas/org-membership/properties/role")
public enum GitHubOrgMembershipRole {
    ADMIN("admin"),
    MEMBER("member"),
    BILLING_MANAGER("billing_manager"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubOrgMembershipRole> WIRE_INDEX =
            EnumUtils.index(GitHubOrgMembershipRole.class, GitHubOrgMembershipRole::wireValue);

    private final String wireValue;

    GitHubOrgMembershipRole(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubOrgMembershipRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
