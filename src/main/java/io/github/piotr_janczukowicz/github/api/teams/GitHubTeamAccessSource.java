package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * How the team's access to the repository was granted. This property is only present when the team is returned in a
 * repository context, such as `GET /repos/{owner}/{repo}/teams`.
 */
@CatalogSchema("components/schemas/team/properties/access_source")
public enum GitHubTeamAccessSource {
    DIRECT("direct"),
    ORGANIZATION("organization"),
    ENTERPRISE("enterprise"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubTeamAccessSource> WIRE_INDEX =
            EnumUtils.index(GitHubTeamAccessSource.class, GitHubTeamAccessSource::wireValue);

    private final String wireValue;

    GitHubTeamAccessSource(final String wireValue) {
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
     * Access source from a wire value.
     *
     * @param value JSON value
     * @return matching access source
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubTeamAccessSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
