package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Which users can access codespaces in an organization. */
@CatalogSchema("operations/codespaces/set-codespaces-access/requestBody/properties/visibility")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespacesAccessVisibility {
    DISABLED("disabled"),
    SELECTED_MEMBERS("selected_members"),
    ALL_MEMBERS("all_members"),
    ALL_MEMBERS_AND_OUTSIDE_COLLABORATORS("all_members_and_outside_collaborators");

    private static final EnumUtils.WireIndex<GitHubCodespacesAccessVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubCodespacesAccessVisibility.class, GitHubCodespacesAccessVisibility::wireValue);

    private final String wireValue;

    GitHubCodespacesAccessVisibility(final String wireValue) {
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
     * Resolves codespaces access visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodespacesAccessVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known codespaces access visibility
     */
    @JsonCreator
    public static GitHubCodespacesAccessVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub codespaces access visibility: " + value));
    }
}
