package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/bypass-requests/secret-scanning}, excluding pagination.
 *
 * @param reviewer Filter bypass requests by the handle of the GitHub user who reviewed the bypass request
 * @param requester Filter bypass requests by the handle of the GitHub user who requested the bypass
 * @param timePeriod The time period to filter by
 * @param requestStatus The status of the bypass request to filter on
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/list-repo-bypass-requests/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListRepoBypassRequestsFilter(
        @Nullable String reviewer,
        @Nullable String requester,
        @Nullable GitHubRuleSuiteTimePeriod timePeriod,
        @Nullable GitHubListRepoBypassRequestsRequestStatus requestStatus) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListRepoBypassRequestsFilter empty() {
        return GitHubListRepoBypassRequestsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (reviewer != null) {
            query.put("reviewer", reviewer);
        }
        if (requester != null) {
            query.put("requester", requester);
        }
        if (timePeriod != null) {
            query.put("time_period", timePeriod.wireValue());
        }
        if (requestStatus != null) {
            query.put("request_status", requestStatus.wireValue());
        }
        return Collections.unmodifiableMap(query);
    }
}
