package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether all repositories have been selected or there's a selection involved. */
@CatalogSchema("components/schemas/scoped-installation/properties/repository_selection")
public enum GitHubScopedInstallationRepositorySelection {
    /** All repositories are selected. */
    ALL("all"),
    /** A selection of repositories is involved. */
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubScopedInstallationRepositorySelection> WIRE_INDEX = EnumUtils.index(
            GitHubScopedInstallationRepositorySelection.class, GitHubScopedInstallationRepositorySelection::wireValue);

    private final String wireValue;

    GitHubScopedInstallationRepositorySelection(final String wireValue) {
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
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubScopedInstallationRepositorySelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub scoped installation repository selection: " + value));
    }
}
