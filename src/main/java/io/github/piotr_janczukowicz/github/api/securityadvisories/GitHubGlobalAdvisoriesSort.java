package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort. */
@CatalogSchema("operations/security-advisories/list-global-advisories/parameters/17")
public enum GitHubGlobalAdvisoriesSort {
    UPDATED("updated"),
    PUBLISHED("published"),
    EPSS_PERCENTAGE("epss_percentage"),
    EPSS_PERCENTILE("epss_percentile");

    private static final EnumUtils.WireIndex<GitHubGlobalAdvisoriesSort> WIRE_INDEX =
            EnumUtils.index(GitHubGlobalAdvisoriesSort.class, GitHubGlobalAdvisoriesSort::wireValue);

    private final String wireValue;

    GitHubGlobalAdvisoriesSort(final String wireValue) {
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
     * Resolves global advisories sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubGlobalAdvisoriesSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known global advisories sort
     */
    @JsonCreator
    public static GitHubGlobalAdvisoriesSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub global advisories sort: " + value));
    }
}
