package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("operations/code-scanning/list-alerts-for-repo/parameters/11")
public enum GitHubListAlertsForRepoSort {
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubListAlertsForRepoSort> WIRE_INDEX =
            EnumUtils.index(GitHubListAlertsForRepoSort.class, GitHubListAlertsForRepoSort::wireValue);

    private final String wireValue;

    GitHubListAlertsForRepoSort(final String wireValue) {
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
     * Resolves list alerts for repo sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListAlertsForRepoSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list alerts for repo sort
     */
    @JsonCreator
    public static GitHubListAlertsForRepoSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list alerts for repo sort: " + value));
    }
}
