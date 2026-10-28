package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("components/parameters/dependabot-alert-sort/schema")
public enum GitHubDependabotAlertSort {
    CREATED("created"),
    UPDATED("updated"),
    EPSS_PERCENTAGE("epss_percentage");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertSort> WIRE_INDEX =
            EnumUtils.index(GitHubDependabotAlertSort.class, GitHubDependabotAlertSort::wireValue);

    private final String wireValue;

    GitHubDependabotAlertSort(final String wireValue) {
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
     * Resolves Dependabot alert sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert sort
     */
    @JsonCreator
    public static GitHubDependabotAlertSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Dependabot alert sort: " + value));
    }
}
