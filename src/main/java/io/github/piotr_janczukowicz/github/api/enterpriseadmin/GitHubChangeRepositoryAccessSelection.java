package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether all or only selected repositories are accessible. */
@CatalogSchema(
        "operations/enterprise-apps/change-installation-repository-access-selection/requestBody/properties/repository_selection")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubChangeRepositoryAccessSelection {
    /** All. */
    ALL("all"),
    /** Selected. */
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubChangeRepositoryAccessSelection> WIRE_INDEX = EnumUtils.index(
            GitHubChangeRepositoryAccessSelection.class, GitHubChangeRepositoryAccessSelection::wireValue);

    private final String wireValue;

    GitHubChangeRepositoryAccessSelection(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubChangeRepositoryAccessSelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub change repository access selection: " + value));
    }
}
