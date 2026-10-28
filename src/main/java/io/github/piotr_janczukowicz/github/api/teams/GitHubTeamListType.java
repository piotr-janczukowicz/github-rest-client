package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Team type. */
@CatalogSchema("components/parameters/team-type/schema")
public enum GitHubTeamListType {
    ALL("all"),
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization");

    private static final EnumUtils.WireIndex<GitHubTeamListType> WIRE_INDEX =
            EnumUtils.index(GitHubTeamListType.class, GitHubTeamListType::wireValue);

    private final String wireValue;

    GitHubTeamListType(final String wireValue) {
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
     * Team list type from a wire value.
     *
     * @param value JSON value
     * @return matching list type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list type
     */
    @JsonCreator
    public static GitHubTeamListType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub team list type: " + value));
    }
}
