package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the user's membership in the team. */
@CatalogSchema("components/schemas/team-membership/properties/state")
public enum GitHubTeamMembershipState {
    ACTIVE("active"),
    PENDING("pending"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubTeamMembershipState> WIRE_INDEX =
            EnumUtils.index(GitHubTeamMembershipState.class, GitHubTeamMembershipState::wireValue);

    private final String wireValue;

    GitHubTeamMembershipState(final String wireValue) {
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
     * Membership state from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubTeamMembershipState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
