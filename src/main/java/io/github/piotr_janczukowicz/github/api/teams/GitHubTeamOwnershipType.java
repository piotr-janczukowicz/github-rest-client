package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The ownership type of the team. */
@CatalogSchema({
    "components/schemas/team/properties/type",
    "components/schemas/team-full/properties/type",
    "components/schemas/team-simple/properties/type"
})
public enum GitHubTeamOwnershipType {
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubTeamOwnershipType> WIRE_INDEX =
            EnumUtils.index(GitHubTeamOwnershipType.class, GitHubTeamOwnershipType::wireValue);

    private final String wireValue;

    GitHubTeamOwnershipType(final String wireValue) {
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
     * Ownership type from a wire value.
     *
     * @param value JSON value
     * @return matching ownership type
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubTeamOwnershipType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
