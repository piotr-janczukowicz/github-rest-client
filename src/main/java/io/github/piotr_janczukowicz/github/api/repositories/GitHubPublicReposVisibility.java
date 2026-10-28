package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility filter of the public repository list of a GitHub Enterprise Server instance. */
public enum GitHubPublicReposVisibility {
    ALL("all"),
    PUBLIC("public");

    private static final EnumUtils.WireIndex<GitHubPublicReposVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubPublicReposVisibility.class, GitHubPublicReposVisibility::wireValue);

    private final String wireValue;

    GitHubPublicReposVisibility(final String wireValue) {
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
     * Public repository list visibility filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubPublicReposVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub public repos visibility: " + value));
    }
}
