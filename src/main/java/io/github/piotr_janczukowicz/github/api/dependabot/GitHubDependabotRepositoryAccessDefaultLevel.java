package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The default repository access level for Dependabot updates. */
@CatalogSchema({
    "components/schemas/dependabot-repository-access-details/properties/default_level",
    "operations/dependabot/set-repository-access-default-level/requestBody/properties/default_level",
    "operations/dependabot/set-repository-access-default-level-for-enterprise/requestBody/properties/default_level"
})
public enum GitHubDependabotRepositoryAccessDefaultLevel {
    PUBLIC("public"),
    INTERNAL("internal");

    private static final EnumUtils.WireIndex<GitHubDependabotRepositoryAccessDefaultLevel> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotRepositoryAccessDefaultLevel.class,
            GitHubDependabotRepositoryAccessDefaultLevel::wireValue);

    private final String wireValue;

    GitHubDependabotRepositoryAccessDefaultLevel(final String wireValue) {
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
     * Resolves Dependabot repository access default level from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotRepositoryAccessDefaultLevel
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot repository access default level
     */
    @JsonCreator
    public static GitHubDependabotRepositoryAccessDefaultLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub Dependabot repository access default level: " + value));
    }
}
