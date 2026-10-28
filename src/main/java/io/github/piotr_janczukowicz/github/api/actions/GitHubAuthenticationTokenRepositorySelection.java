package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether all repositories have been selected or there is a selection involved. */
@CatalogSchema("components/schemas/authentication-token/properties/repository_selection")
public enum GitHubAuthenticationTokenRepositorySelection {
    ALL("all"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubAuthenticationTokenRepositorySelection> WIRE_INDEX = EnumUtils.index(
            GitHubAuthenticationTokenRepositorySelection.class,
            GitHubAuthenticationTokenRepositorySelection::wireValue);

    private final String wireValue;

    GitHubAuthenticationTokenRepositorySelection(final String wireValue) {
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
    public static GitHubAuthenticationTokenRepositorySelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubAuthenticationTokenRepositorySelection value: " + value));
    }
}
