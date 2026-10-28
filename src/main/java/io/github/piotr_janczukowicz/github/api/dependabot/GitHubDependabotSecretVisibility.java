package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a secret. */
@CatalogSchema({
    "components/schemas/organization-dependabot-secret/properties/visibility",
    "operations/dependabot/create-or-update-org-secret/requestBody/properties/visibility"
})
public enum GitHubDependabotSecretVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubDependabotSecretVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotSecretVisibility.class, GitHubDependabotSecretVisibility::wireValue);

    private final String wireValue;

    GitHubDependabotSecretVisibility(final String wireValue) {
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
     * Resolves Dependabot secret visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotSecretVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot secret visibility
     */
    @JsonCreator
    public static GitHubDependabotSecretVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Dependabot secret visibility: " + value));
    }
}
