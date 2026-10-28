package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether a dependency is requested directly by a manifest or is a dependency of another dependency. */
@CatalogSchema("components/schemas/dependency/properties/relationship")
public enum GitHubDependencyRelationship {
    DIRECT("direct"),
    INDIRECT("indirect");

    private static final EnumUtils.WireIndex<GitHubDependencyRelationship> WIRE_INDEX =
            EnumUtils.index(GitHubDependencyRelationship.class, GitHubDependencyRelationship::wireValue);

    private final String wireValue;

    GitHubDependencyRelationship(final String wireValue) {
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
     * dependency relationship from a wire value.
     *
     * @param value JSON value
     * @return matching dependency relationship
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known dependency relationship
     */
    @JsonCreator
    public static GitHubDependencyRelationship fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub dependency relationship: " + value));
    }
}
