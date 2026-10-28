package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for pull request review comment list queries. */
@CatalogSchema("operations/pulls/list-review-comments/parameters/3")
public enum GitHubListPullReviewCommentsSort {
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubListPullReviewCommentsSort> WIRE_INDEX =
            EnumUtils.index(GitHubListPullReviewCommentsSort.class, GitHubListPullReviewCommentsSort::wireValue);

    private final String wireValue;

    GitHubListPullReviewCommentsSort(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Sort field for pull request review comment list {@code sort} parameters.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubListPullReviewCommentsSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list pull review comments sort: " + value));
    }
}
