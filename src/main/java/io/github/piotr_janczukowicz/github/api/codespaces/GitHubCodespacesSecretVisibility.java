package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a Codespaces secret. */
@CatalogSchema({
    "components/schemas/codespaces-org-secret/properties/visibility",
    "components/schemas/codespaces-secret/properties/visibility",
    "operations/codespaces/create-or-update-org-secret/requestBody/properties/visibility"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespacesSecretVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubCodespacesSecretVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubCodespacesSecretVisibility.class, GitHubCodespacesSecretVisibility::wireValue);

    private final String wireValue;

    GitHubCodespacesSecretVisibility(final String wireValue) {
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
     * Resolves Codespaces secret visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodespacesSecretVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Codespaces secret visibility
     */
    @JsonCreator
    public static GitHubCodespacesSecretVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Codespaces secret visibility: " + value));
    }
}
