package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for GitHub Search issues {@code sort} query parameter. */
@CatalogSchema("operations/search/issues-and-pull-requests/parameters/1")
public enum GitHubIssueSearchSort {
    COMMENTS("comments"),
    REACTIONS("reactions"),
    REACTIONS_PLUS_ONE("reactions-+1"),
    REACTIONS_MINUS_ONE("reactions--1"),
    REACTIONS_SMILE("reactions-smile"),
    REACTIONS_THINKING_FACE("reactions-thinking_face"),
    REACTIONS_HEART("reactions-heart"),
    REACTIONS_TADA("reactions-tada"),
    INTERACTIONS("interactions"),
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubIssueSearchSort> WIRE_INDEX =
            EnumUtils.index(GitHubIssueSearchSort.class, GitHubIssueSearchSort::wireValue);

    private final String wireValue;

    GitHubIssueSearchSort(final String wireValue) {
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
    public static GitHubIssueSearchSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue search sort: " + value));
    }
}
