package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Fork list sort order. */
@CatalogSchema("operations/repos/list-forks/parameters/2")
public enum GitHubRepoForksSort {
    NEWEST("newest"),
    OLDEST("oldest"),
    STARGAZERS("stargazers"),
    WATCHERS("watchers");

    private static final EnumUtils.WireIndex<GitHubRepoForksSort> WIRE_INDEX =
            EnumUtils.index(GitHubRepoForksSort.class, GitHubRepoForksSort::wireValue);

    private final String wireValue;

    GitHubRepoForksSort(final String wireValue) {
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
     * Fork list sort order from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepoForksSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub repo forks sort: " + value));
    }
}
