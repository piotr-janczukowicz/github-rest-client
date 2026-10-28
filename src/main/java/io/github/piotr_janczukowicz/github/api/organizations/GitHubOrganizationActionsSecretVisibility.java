package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a secret. */
@CatalogSchema("components/schemas/organization-actions-secret/properties/visibility")
public enum GitHubOrganizationActionsSecretVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubOrganizationActionsSecretVisibility> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationActionsSecretVisibility.class, GitHubOrganizationActionsSecretVisibility::wireValue);

    private final String wireValue;

    GitHubOrganizationActionsSecretVisibility(final String wireValue) {
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
     * Resolves organization Actions secret visibility from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubOrganizationActionsSecretVisibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known organization Actions secret visibility
     */
    @JsonCreator
    public static GitHubOrganizationActionsSecretVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization Actions secret visibility: " + value));
    }
}
