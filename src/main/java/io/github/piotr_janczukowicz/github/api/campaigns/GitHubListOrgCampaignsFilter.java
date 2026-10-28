package io.github.piotr_janczukowicz.github.api.campaigns;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/campaigns}, excluding pagination.
 *
 * @param direction The direction to sort the results by.
 * @param state If specified, only campaigns with this state will be returned.
 * @param sort The property by which to sort the results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/campaigns/list-org-campaigns/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListOrgCampaignsFilter(
        @Nullable GitHubDirection direction,
        @Nullable GitHubCampaignState state,
        @Nullable GitHubListOrgCampaignsSort sort) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListOrgCampaignsFilter empty() {
        return GitHubListOrgCampaignsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
