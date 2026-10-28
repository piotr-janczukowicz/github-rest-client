package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property by which to sort the results. */
@CatalogSchema("operations/campaigns/list-org-campaigns/parameters/5")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListOrgCampaignsSort {
    /** Sort by creation date. */
    CREATED("created"),
    /** Sort by last update date. */
    UPDATED("updated"),
    /** Sort by end date. */
    ENDS_AT("ends_at"),
    /** Sort by publication date. */
    PUBLISHED("published");

    private static final EnumUtils.WireIndex<GitHubListOrgCampaignsSort> WIRE_INDEX =
            EnumUtils.index(GitHubListOrgCampaignsSort.class, GitHubListOrgCampaignsSort::wireValue);

    private final String wireValue;

    GitHubListOrgCampaignsSort(final String wireValue) {
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
     * Resolves list org campaigns sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListOrgCampaignsSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list org campaigns sort
     */
    @JsonCreator
    public static GitHubListOrgCampaignsSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list org campaigns sort: " + value));
    }
}
