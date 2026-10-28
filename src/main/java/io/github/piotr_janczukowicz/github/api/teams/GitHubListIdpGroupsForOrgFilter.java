package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/team-sync/groups}, excluding pagination.
 *
 * @param q Limits the list to groups containing the text in the group name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/list-idp-groups-for-org/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListIdpGroupsForOrgFilter(@Nullable String q) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListIdpGroupsForOrgFilter empty() {
        return GitHubListIdpGroupsForOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (q == null) {
            return Map.of();
        }
        return Map.of("q", q);
    }
}
