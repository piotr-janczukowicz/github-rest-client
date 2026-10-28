package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for GitHub Search commits {@code sort} query parameter. */
@CatalogSchema("operations/search/commits/parameters/1")
public enum GitHubCommitSearchSort {
    AUTHOR_DATE("author-date"),
    COMMITTER_DATE("committer-date");

    private static final EnumUtils.WireIndex<GitHubCommitSearchSort> WIRE_INDEX =
            EnumUtils.index(GitHubCommitSearchSort.class, GitHubCommitSearchSort::wireValue);

    private final String wireValue;

    GitHubCommitSearchSort(final String wireValue) {
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
    public static GitHubCommitSearchSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub commit search sort: " + value));
    }
}
