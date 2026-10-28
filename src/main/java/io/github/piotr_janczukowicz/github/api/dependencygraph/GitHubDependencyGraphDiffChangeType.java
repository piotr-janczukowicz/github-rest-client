package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether a dependency was added or removed between two commits. */
@CatalogSchema("components/schemas/dependency-graph-diff/items/properties/change_type")
public enum GitHubDependencyGraphDiffChangeType {
    ADDED("added"),
    REMOVED("removed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDependencyGraphDiffChangeType> WIRE_INDEX =
            EnumUtils.index(GitHubDependencyGraphDiffChangeType.class, GitHubDependencyGraphDiffChangeType::wireValue);

    private final String wireValue;

    GitHubDependencyGraphDiffChangeType(final String wireValue) {
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
     * dependency change type from a wire value.
     *
     * @param value JSON value
     * @return matching dependency change type
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependencyGraphDiffChangeType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
