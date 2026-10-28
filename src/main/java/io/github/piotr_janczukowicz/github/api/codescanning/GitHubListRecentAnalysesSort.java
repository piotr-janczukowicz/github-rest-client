package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("operations/code-scanning/list-recent-analyses/parameters/10")
public enum GitHubListRecentAnalysesSort {
    CREATED("created");

    private static final EnumUtils.WireIndex<GitHubListRecentAnalysesSort> WIRE_INDEX =
            EnumUtils.index(GitHubListRecentAnalysesSort.class, GitHubListRecentAnalysesSort::wireValue);

    private final String wireValue;

    GitHubListRecentAnalysesSort(final String wireValue) {
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
     * Resolves list recent analyses sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListRecentAnalysesSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list recent analyses sort
     */
    @JsonCreator
    public static GitHubListRecentAnalysesSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list recent analyses sort: " + value));
    }
}
