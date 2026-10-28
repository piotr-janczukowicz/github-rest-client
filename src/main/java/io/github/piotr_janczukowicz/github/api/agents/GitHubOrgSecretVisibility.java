package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Which type of organization repositories have access to the organization secret. */
@CatalogSchema("operations/agents/create-or-update-org-secret/requestBody/properties/visibility")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrgSecretVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubOrgSecretVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubOrgSecretVisibility.class, GitHubOrgSecretVisibility::wireValue);

    private final String wireValue;

    GitHubOrgSecretVisibility(final String wireValue) {
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
     * Resolves organization secret visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubOrgSecretVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known organization secret visibility
     */
    @JsonCreator
    public static GitHubOrgSecretVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization secret visibility: " + value));
    }
}
