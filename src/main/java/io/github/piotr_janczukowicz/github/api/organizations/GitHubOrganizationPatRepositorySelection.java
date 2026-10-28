package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Organization programmatic access grant repository selection. */
@CatalogSchema("components/schemas/organization-programmatic-access-grant/properties/repository_selection")
public enum GitHubOrganizationPatRepositorySelection {
    NONE("none"),
    ALL("all"),
    SUBSET("subset");

    private static final EnumUtils.WireIndex<GitHubOrganizationPatRepositorySelection> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPatRepositorySelection.class, GitHubOrganizationPatRepositorySelection::wireValue);

    private final String wireValue;

    GitHubOrganizationPatRepositorySelection(final String wireValue) {
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
     * Repository selection from a wire value.
     *
     * @param value JSON value
     * @return matching repository selection
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known repository selection
     */
    @JsonCreator
    public static GitHubOrganizationPatRepositorySelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub organization PAT repository selection: " + value));
    }
}
