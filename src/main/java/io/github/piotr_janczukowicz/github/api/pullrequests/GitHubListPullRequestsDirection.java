package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request list sort direction. */
@CatalogSchema({
    "operations/pulls/list/parameters/6",
    "operations/pulls/list-review-comments/parameters/4",
    "operations/pulls/list-review-comments-for-repo/parameters/3"
})
public enum GitHubListPullRequestsDirection {
    ASC("asc"),
    DESC("desc");

    private static final EnumUtils.WireIndex<GitHubListPullRequestsDirection> WIRE_INDEX =
            EnumUtils.index(GitHubListPullRequestsDirection.class, GitHubListPullRequestsDirection::wireValue);

    private final String wireValue;

    GitHubListPullRequestsDirection(final String wireValue) {
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
     * Sort direction for GitHub pull request list {@code direction} parameters.
     *
     * @param value query parameter value
     * @return matching direction
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known direction
     */
    @JsonCreator
    public static GitHubListPullRequestsDirection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list pull requests direction: " + value));
    }
}
