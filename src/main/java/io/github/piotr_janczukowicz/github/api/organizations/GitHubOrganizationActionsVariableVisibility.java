package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a variable. */
@CatalogSchema("components/schemas/organization-actions-variable/properties/visibility")
public enum GitHubOrganizationActionsVariableVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubOrganizationActionsVariableVisibility> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationActionsVariableVisibility.class, GitHubOrganizationActionsVariableVisibility::wireValue);

    private final String wireValue;

    GitHubOrganizationActionsVariableVisibility(final String wireValue) {
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
     * Resolves organization Actions variable visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubOrganizationActionsVariableVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known organization Actions variable visibility
     */
    @JsonCreator
    public static GitHubOrganizationActionsVariableVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization Actions variable visibility: " + value));
    }
}
