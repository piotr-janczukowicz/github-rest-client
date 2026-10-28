package io.github.piotr_janczukowicz.github.api.securityadvisories;

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
 * Query filters for {@code GET /orgs/{org}/security-advisories} and {@code GET
 * /repos/{owner}/{repo}/security-advisories}, excluding pagination.
 *
 * @param direction The direction to sort the results by.
 * @param sort The property to sort the results by.
 * @param state Filter advisories by state.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/security-advisories/list-org-repository-advisories/parameters",
    "operations/security-advisories/list-repository-advisories/parameters"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepositoryAdvisoriesFilter(
        @Nullable GitHubDirection direction,
        @Nullable GitHubRepositoryAdvisoriesSort sort,
        @Nullable GitHubRepositoryAdvisoriesFilterState state) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListRepositoryAdvisoriesFilter empty() {
        return GitHubListRepositoryAdvisoriesFilterBuilder.builder().build();
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
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
