package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Issue search type for the {@code search_type} query parameter on {@code GET /search/issues}. */
@CatalogSchema("components/parameters/search-type/schema")
public enum GitHubIssueSearchQueryType {
    SEMANTIC("semantic"),
    HYBRID("hybrid");

    private static final EnumUtils.WireIndex<GitHubIssueSearchQueryType> WIRE_INDEX =
            EnumUtils.index(GitHubIssueSearchQueryType.class, GitHubIssueSearchQueryType::wireValue);

    private final String wireValue;

    GitHubIssueSearchQueryType(final String wireValue) {
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
     * Issue search query type from a wire value.
     *
     * @param value query parameter value
     * @return matching query type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known query type
     */
    @JsonCreator
    public static GitHubIssueSearchQueryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue search query type: " + value));
    }
}
