package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for repository pull request review comment list queries. */
@CatalogSchema("operations/pulls/list-review-comments-for-repo/parameters/2")
public enum GitHubListRepoReviewCommentsSort {
    CREATED("created"),
    UPDATED("updated"),
    CREATED_AT("created_at");

    private static final EnumUtils.WireIndex<GitHubListRepoReviewCommentsSort> WIRE_INDEX =
            EnumUtils.index(GitHubListRepoReviewCommentsSort.class, GitHubListRepoReviewCommentsSort::wireValue);

    private final String wireValue;

    GitHubListRepoReviewCommentsSort(final String wireValue) {
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
     * Sort field for repository review comment list {@code sort} parameters.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubListRepoReviewCommentsSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list repo review comments sort: " + value));
    }
}
