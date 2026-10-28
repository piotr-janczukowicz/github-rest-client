package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The level of privacy this team should have. */
@CatalogSchema({
    "components/schemas/team-full/properties/privacy",
    "operations/teams/create/requestBody/properties/privacy",
    "operations/teams/update-in-org/requestBody/properties/privacy",
    "operations/teams/update-legacy/requestBody/properties/privacy"
})
public enum GitHubTeamPrivacy {
    SECRET("secret"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubTeamPrivacy> WIRE_INDEX =
            EnumUtils.index(GitHubTeamPrivacy.class, GitHubTeamPrivacy::wireValue);

    private final String wireValue;

    GitHubTeamPrivacy(final String wireValue) {
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
     * Privacy from a wire value.
     *
     * @param value JSON value
     * @return matching privacy
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known privacy
     */
    @JsonCreator
    public static GitHubTeamPrivacy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub team privacy: " + value));
    }
}
