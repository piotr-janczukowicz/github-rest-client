package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository issue comment list {@code sort} query parameter. */
@CatalogSchema("operations/issues/list-comments-for-repo/parameters/2")
public enum GitHubListCommentsForRepoSort {
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubListCommentsForRepoSort> WIRE_INDEX =
            EnumUtils.index(GitHubListCommentsForRepoSort.class, GitHubListCommentsForRepoSort::wireValue);

    private final String wireValue;

    GitHubListCommentsForRepoSort(final String wireValue) {
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
    public static GitHubListCommentsForRepoSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list comments for repo sort: " + value));
    }
}
