package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Where the dependency is utilized. */
@CatalogSchema("components/schemas/dependency-graph-diff/items/properties/scope")
public enum GitHubDependencyGraphDiffScope {
    UNKNOWN("unknown"),
    RUNTIME("runtime"),
    DEVELOPMENT("development");

    private static final EnumUtils.WireIndex<GitHubDependencyGraphDiffScope> WIRE_INDEX =
            EnumUtils.index(GitHubDependencyGraphDiffScope.class, GitHubDependencyGraphDiffScope::wireValue);

    private final String wireValue;

    GitHubDependencyGraphDiffScope(final String wireValue) {
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
     * dependency graph diff scope from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependencyGraphDiffScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
