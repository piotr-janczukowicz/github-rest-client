package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Authenticated user repository list visibility filter. */
public enum GitHubAuthenticatedUserReposVisibility {
    ALL("all"),
    PUBLIC("public"),
    PRIVATE("private");

    private static final EnumUtils.WireIndex<GitHubAuthenticatedUserReposVisibility> WIRE_INDEX = EnumUtils.index(
            GitHubAuthenticatedUserReposVisibility.class, GitHubAuthenticatedUserReposVisibility::wireValue);

    private final String wireValue;

    GitHubAuthenticatedUserReposVisibility(final String wireValue) {
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
     * Authenticated user repository list visibility filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAuthenticatedUserReposVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub authenticated user repos visibility: " + value));
    }
}
