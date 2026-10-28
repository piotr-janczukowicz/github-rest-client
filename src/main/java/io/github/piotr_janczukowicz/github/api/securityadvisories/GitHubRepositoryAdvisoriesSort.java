package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort. */
@CatalogSchema({
    "operations/security-advisories/list-org-repository-advisories/parameters/2",
    "operations/security-advisories/list-repository-advisories/parameters/3"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryAdvisoriesSort {
    CREATED("created"),
    UPDATED("updated"),
    PUBLISHED("published");

    private static final EnumUtils.WireIndex<GitHubRepositoryAdvisoriesSort> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryAdvisoriesSort.class, GitHubRepositoryAdvisoriesSort::wireValue);

    private final String wireValue;

    GitHubRepositoryAdvisoriesSort(final String wireValue) {
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
     * Resolves repository advisories sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubRepositoryAdvisoriesSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known repository advisories sort
     */
    @JsonCreator
    public static GitHubRepositoryAdvisoriesSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub repository advisories sort: " + value));
    }
}
