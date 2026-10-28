package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Sort field for GitHub Search code {@code sort} query parameter.
 *
 * @deprecated This field is closing down. Sorts the results of your query. Can only be {@code indexed}.
 */
@Deprecated
@CatalogSchema("operations/search/code/parameters/1")
public enum GitHubCodeSearchSort {
    INDEXED("indexed");

    private static final EnumUtils.WireIndex<GitHubCodeSearchSort> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSearchSort.class, GitHubCodeSearchSort::wireValue);

    private final String wireValue;

    GitHubCodeSearchSort(final String wireValue) {
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
     * Sort field from a wire value.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubCodeSearchSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub code search sort: " + value));
    }
}
