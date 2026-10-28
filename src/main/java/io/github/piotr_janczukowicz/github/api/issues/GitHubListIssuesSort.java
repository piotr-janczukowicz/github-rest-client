package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Issue list {@code sort} query parameter. */
@CatalogSchema({
    "operations/issues/list/parameters/3",
    "operations/issues/list-for-org/parameters/5",
    "operations/issues/list-for-repo/parameters/10",
    "operations/issues/list-for-authenticated-user/parameters/3"
})
public enum GitHubListIssuesSort {
    CREATED("created"),
    UPDATED("updated"),
    COMMENTS("comments");

    private static final EnumUtils.WireIndex<GitHubListIssuesSort> WIRE_INDEX =
            EnumUtils.index(GitHubListIssuesSort.class, GitHubListIssuesSort::wireValue);

    private final String wireValue;

    GitHubListIssuesSort(final String wireValue) {
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
     * Sort field from a wire value.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubListIssuesSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list issues sort: " + value));
    }
}
