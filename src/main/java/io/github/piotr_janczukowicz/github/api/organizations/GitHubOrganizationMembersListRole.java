package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Query enum for {@code GET /orgs/{org}/members} {@code role} ({@code all}/{@code admin}/{@code member}).
 *
 * <p>Distinct from {@link GitHubOrganizationMembershipRole} on {@code orgs/set-membership-for-user} and
 * {@link GitHubOrgMembershipRole} on {@code org-membership}.
 */
@CatalogSchema("operations/orgs/list-members/parameters/2")
public enum GitHubOrganizationMembersListRole {
    ALL("all"),
    ADMIN("admin"),
    MEMBER("member");

    private static final EnumUtils.WireIndex<GitHubOrganizationMembersListRole> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationMembersListRole.class, GitHubOrganizationMembersListRole::wireValue);

    private final String wireValue;

    GitHubOrganizationMembersListRole(final String wireValue) {
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
     * List role from a wire value.
     *
     * @param value query parameter value
     * @return matching list role
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list role
     */
    @JsonCreator
    public static GitHubOrganizationMembersListRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization members list role: " + value));
    }
}
