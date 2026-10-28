package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The member's role on the team. Only present on the `List team members` endpoint, and only when the feature is enabled
 * for the organization.
 */
@CatalogSchema({
    "components/schemas/team-member/properties/role",
    "components/schemas/team-membership/properties/role",
    "operations/teams/add-or-update-membership-for-user-in-org/requestBody/properties/role",
    "operations/teams/add-or-update-membership-for-user-legacy/requestBody/properties/role"
})
public enum GitHubTeamRole {
    MEMBER("member"),
    MAINTAINER("maintainer");

    private static final EnumUtils.WireIndex<GitHubTeamRole> WIRE_INDEX =
            EnumUtils.index(GitHubTeamRole.class, GitHubTeamRole::wireValue);

    private final String wireValue;

    GitHubTeamRole(final String wireValue) {
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
     * Team role from a wire value.
     *
     * @param value JSON value
     * @return matching role
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known role
     */
    @JsonCreator
    public static GitHubTeamRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub team role: " + value));
    }
}
