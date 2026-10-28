package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("operations/code-scanning/list-alerts-for-enterprise/parameters/9")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubListAlertsForEnterpriseSort {
    /** created. */
    CREATED("created"),
    /** updated. */
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubListAlertsForEnterpriseSort> WIRE_INDEX =
            EnumUtils.index(GitHubListAlertsForEnterpriseSort.class, GitHubListAlertsForEnterpriseSort::wireValue);

    private final String wireValue;

    GitHubListAlertsForEnterpriseSort(final String wireValue) {
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
     * Resolves the property by which to sort the results from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubListAlertsForEnterpriseSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list alerts for enterprise sort: " + value));
    }
}
