package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The scope of the vulnerable dependency. */
@CatalogSchema("components/parameters/dependabot-alert-scope/schema")
public enum GitHubDependabotAlertScope {
    DEVELOPMENT("development"),
    RUNTIME("runtime");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertScope> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotAlertScope.class, GitHubDependabotAlertScope::wireValue);

    private final String wireValue;

    GitHubDependabotAlertScope(final String wireValue) {
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
     * Resolves Dependabot alert scope from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertScope
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert scope
     */
    @JsonCreator
    public static GitHubDependabotAlertScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Dependabot alert scope: " + value));
    }
}
