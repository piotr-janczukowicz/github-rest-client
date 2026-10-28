package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Hovercard subject type for {@code GET /users/{username}/hovercard}.
 *
 * <p>Wire values: {@code organization}, {@code repository}, {@code issue}, {@code pull_request}.
 */
@CatalogSchema("operations/users/get-context-for-user/parameters/1")
public enum GitHubUserContextSubjectType {
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    ISSUE("issue"),
    PULL_REQUEST("pull_request");

    private static final EnumUtils.WireIndex<GitHubUserContextSubjectType> WIRE_INDEX =
            EnumUtils.index(GitHubUserContextSubjectType.class, GitHubUserContextSubjectType::wireValue);

    private final String wireValue;

    GitHubUserContextSubjectType(final String wireValue) {
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
     * Hovercard subject type from a wire value.
     *
     * @param value JSON value
     * @return matching subject type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known subject type
     */
    @JsonCreator
    public static GitHubUserContextSubjectType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub user context subject type: " + value));
    }
}
