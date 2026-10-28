package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("operations/code-scanning/list-alerts-for-org/parameters/9")
public enum GitHubListAlertsForOrgSort {
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubListAlertsForOrgSort> WIRE_INDEX =
            EnumUtils.index(GitHubListAlertsForOrgSort.class, GitHubListAlertsForOrgSort::wireValue);

    private final String wireValue;

    GitHubListAlertsForOrgSort(final String wireValue) {
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
     * Resolves list alerts for org sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListAlertsForOrgSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list alerts for org sort
     */
    @JsonCreator
    public static GitHubListAlertsForOrgSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list alerts for org sort: " + value));
    }
}
