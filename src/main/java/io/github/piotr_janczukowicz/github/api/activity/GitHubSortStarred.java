package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for starred repositories query parameter. */
@CatalogSchema("components/parameters/sort-starred/schema")
public enum GitHubSortStarred {
    /** Sort by the date the repository was starred. */
    CREATED("created"),
    /** Sort by the date the repository was last updated. */
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubSortStarred> WIRE_INDEX =
            EnumUtils.index(GitHubSortStarred.class, GitHubSortStarred::wireValue);

    private final String wireValue;

    GitHubSortStarred(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this sort field.
     *
     * @return {@code created} or {@code updated}
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses GitHub's {@code sort} query value for starred repositories.
     *
     * @param value wire value
     * @return sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubSortStarred fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub sort: " + value));
    }
}
