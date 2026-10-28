package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The execution scope of the vulnerable dependency. */
@CatalogSchema({
    "components/schemas/dependabot-alert/properties/dependency/properties/scope",
    "components/schemas/dependabot-alert-with-repository/properties/dependency/properties/scope"
})
public enum GitHubDependabotAlertDependencyScope {
    DEVELOPMENT("development"),
    RUNTIME("runtime");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertDependencyScope> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotAlertDependencyScope.class, GitHubDependabotAlertDependencyScope::wireValue);

    private final String wireValue;

    GitHubDependabotAlertDependencyScope(final String wireValue) {
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
     * Resolves Dependabot alert dependency scope from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertDependencyScope
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert dependency scope
     */
    @JsonCreator
    public static GitHubDependabotAlertDependencyScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub Dependabot alert dependency scope: " + value));
    }
}
