package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Query enum for {@code GET …/members} {@code role} ({@code member}/{@code maintainer}/{@code all}).
 *
 * <p>Distinct from {@link GitHubTeamRole} on {@code team-member} / membership payloads (no {@code all}).
 */
@CatalogSchema({"operations/teams/list-members-in-org/parameters/2", "operations/teams/list-members-legacy/parameters/1"
})
public enum GitHubTeamMembersListRole {
    MEMBER("member"),
    MAINTAINER("maintainer"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubTeamMembersListRole> WIRE_INDEX =
            EnumUtils.index(GitHubTeamMembersListRole.class, GitHubTeamMembersListRole::wireValue);

    private final String wireValue;

    GitHubTeamMembersListRole(final String wireValue) {
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
     * Members list role from a wire value.
     *
     * @param value JSON value
     * @return matching role
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known role
     */
    @JsonCreator
    public static GitHubTeamMembersListRole fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub team members list role: " + value));
    }
}
