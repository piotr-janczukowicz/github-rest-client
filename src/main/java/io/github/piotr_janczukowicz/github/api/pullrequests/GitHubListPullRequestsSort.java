package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request list sort field. */
@CatalogSchema("operations/pulls/list/parameters/5")
public enum GitHubListPullRequestsSort {
    CREATED("created"),
    UPDATED("updated"),
    POPULARITY("popularity"),
    LONG_RUNNING("long-running");

    private static final EnumUtils.WireIndex<GitHubListPullRequestsSort> WIRE_INDEX =
            EnumUtils.index(GitHubListPullRequestsSort.class, GitHubListPullRequestsSort::wireValue);

    private final String wireValue;

    GitHubListPullRequestsSort(final String wireValue) {
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
     * Sort field for GitHub pull request list {@code sort} parameters.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubListPullRequestsSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list pull requests sort: " + value));
    }
}
