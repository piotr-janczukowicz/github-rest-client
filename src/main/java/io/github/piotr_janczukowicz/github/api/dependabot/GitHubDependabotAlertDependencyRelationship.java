package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The vulnerable dependency's relationship to your project. */
@CatalogSchema({
    "components/schemas/dependabot-alert/properties/dependency/properties/relationship",
    "components/schemas/dependabot-alert-with-repository/properties/dependency/properties/relationship"
})
public enum GitHubDependabotAlertDependencyRelationship {
    UNKNOWN("unknown"),
    DIRECT("direct"),
    TRANSITIVE("transitive"),
    INCONCLUSIVE("inconclusive");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertDependencyRelationship> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotAlertDependencyRelationship.class, GitHubDependabotAlertDependencyRelationship::wireValue);

    private final String wireValue;

    GitHubDependabotAlertDependencyRelationship(final String wireValue) {
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
     * Resolves Dependabot alert dependency relationship from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertDependencyRelationship
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert dependency relationship
     */
    @JsonCreator
    public static GitHubDependabotAlertDependencyRelationship fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub Dependabot alert dependency relationship: " + value));
    }
}
