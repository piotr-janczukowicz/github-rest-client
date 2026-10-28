package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Installation repository selection. */
@CatalogSchema("components/schemas/installation/properties/repository_selection")
public enum GitHubInstallationRepositorySelection {
    ALL("all"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubInstallationRepositorySelection> WIRE_INDEX = EnumUtils.index(
            GitHubInstallationRepositorySelection.class, GitHubInstallationRepositorySelection::wireValue);

    private final String wireValue;

    GitHubInstallationRepositorySelection(final String wireValue) {
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
    public static GitHubInstallationRepositorySelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub installation repository selection: " + value));
    }
}
