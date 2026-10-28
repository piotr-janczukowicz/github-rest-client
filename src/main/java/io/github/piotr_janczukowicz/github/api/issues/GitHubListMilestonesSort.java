package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Milestone list {@code sort} query parameter. */
@CatalogSchema("operations/issues/list-milestones/parameters/3")
public enum GitHubListMilestonesSort {
    DUE_ON("due_on"),
    COMPLETENESS("completeness");

    private static final EnumUtils.WireIndex<GitHubListMilestonesSort> WIRE_INDEX =
            EnumUtils.index(GitHubListMilestonesSort.class, GitHubListMilestonesSort::wireValue);

    private final String wireValue;

    GitHubListMilestonesSort(final String wireValue) {
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
    public static GitHubListMilestonesSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list milestones sort: " + value));
    }
}
