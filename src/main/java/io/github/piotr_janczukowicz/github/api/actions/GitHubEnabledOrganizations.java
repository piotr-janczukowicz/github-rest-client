package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The policy that controls the organizations in the enterprise that are allowed to run GitHub Actions. */
@CatalogSchema("components/schemas/enabled-organizations")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubEnabledOrganizations {
    ALL("all"),
    NONE("none"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubEnabledOrganizations> WIRE_INDEX =
            EnumUtils.index(GitHubEnabledOrganizations.class, GitHubEnabledOrganizations::wireValue);

    private final String wireValue;

    GitHubEnabledOrganizations(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubEnabledOrganizations fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubEnabledOrganizations value: " + value));
    }
}
