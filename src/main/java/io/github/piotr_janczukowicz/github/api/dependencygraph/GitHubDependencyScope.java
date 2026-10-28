package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether the dependency is required for the primary build artifact (runtime) or is only used for development. */
@CatalogSchema("components/schemas/dependency/properties/scope")
public enum GitHubDependencyScope {
    RUNTIME("runtime"),
    DEVELOPMENT("development");

    private static final EnumUtils.WireIndex<GitHubDependencyScope> WIRE_INDEX =
            EnumUtils.index(GitHubDependencyScope.class, GitHubDependencyScope::wireValue);

    private final String wireValue;

    GitHubDependencyScope(final String wireValue) {
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
     * dependency scope from a wire value.
     *
     * @param value JSON value
     * @return matching dependency scope
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known dependency scope
     */
    @JsonCreator
    public static GitHubDependencyScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub dependency scope: " + value));
    }
}
