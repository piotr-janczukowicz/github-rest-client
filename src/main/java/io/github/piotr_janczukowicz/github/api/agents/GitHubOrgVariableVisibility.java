package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Which type of organization repositories have access to the organization variable. */
@CatalogSchema({
    "operations/agents/create-org-variable/requestBody/properties/visibility",
    "operations/agents/update-org-variable/requestBody/properties/visibility"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrgVariableVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubOrgVariableVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubOrgVariableVisibility.class, GitHubOrgVariableVisibility::wireValue);

    private final String wireValue;

    GitHubOrgVariableVisibility(final String wireValue) {
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
     * Resolves organization variable visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubOrgVariableVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known organization variable visibility
     */
    @JsonCreator
    public static GitHubOrgVariableVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub organization variable visibility: " + value));
    }
}
