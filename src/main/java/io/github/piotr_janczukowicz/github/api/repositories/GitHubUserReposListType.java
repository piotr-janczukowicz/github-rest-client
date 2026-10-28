package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** User repository list type filter for {@code GET /users/{username}/repos}. */
@CatalogSchema("operations/repos/list-for-user/parameters/1")
public enum GitHubUserReposListType {
    ALL("all"),
    OWNER("owner"),
    MEMBER("member");

    private static final EnumUtils.WireIndex<GitHubUserReposListType> WIRE_INDEX =
            EnumUtils.index(GitHubUserReposListType.class, GitHubUserReposListType::wireValue);

    private final String wireValue;

    GitHubUserReposListType(final String wireValue) {
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
     * User repository list type filter for {@code GET /users/{username}/repos} from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUserReposListType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub user repos list type: " + value));
    }
}
