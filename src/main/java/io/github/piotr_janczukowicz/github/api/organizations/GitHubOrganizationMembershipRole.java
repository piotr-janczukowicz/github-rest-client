package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Role to assign when setting organization membership for a user ({@code admin}/{@code member}).
 *
 * <p>Distinct from {@link GitHubOrgMembershipRole} on the {@code org-membership} response.
 */
@CatalogSchema("operations/orgs/set-membership-for-user/requestBody/properties/role")
public enum GitHubOrganizationMembershipRole {
    ADMIN("admin"),
    MEMBER("member");

    private static final EnumUtils.WireIndex<GitHubOrganizationMembershipRole> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationMembershipRole.class, GitHubOrganizationMembershipRole::wireValue);

    private final String wireValue;

    GitHubOrganizationMembershipRole(final String wireValue) {
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
    public static GitHubOrganizationMembershipRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization membership role: " + value));
    }
}
