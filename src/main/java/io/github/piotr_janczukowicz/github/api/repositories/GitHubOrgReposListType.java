package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Organization repository list type filter. */
public enum GitHubOrgReposListType {
    ALL("all"),
    PUBLIC("public"),
    PRIVATE("private"),
    FORKS("forks"),
    SOURCES("sources"),
    MEMBER("member"),
    INTERNAL("internal");

    private static final EnumUtils.WireIndex<GitHubOrgReposListType> WIRE_INDEX =
            EnumUtils.index(GitHubOrgReposListType.class, GitHubOrgReposListType::wireValue);

    private final String wireValue;

    GitHubOrgReposListType(final String wireValue) {
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
     * Organization repository list type filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubOrgReposListType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub org repos list type: " + value));
    }
}
