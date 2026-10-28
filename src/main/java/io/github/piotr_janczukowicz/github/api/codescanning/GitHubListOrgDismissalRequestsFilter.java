package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/dismissal-requests/code-scanning}, excluding pagination.
 *
 * @param reviewer Filter dismissal requests by the handle of the GitHub user who reviewed the request
 * @param requester Filter dismissal requests by the handle of the GitHub user who requested the dismissal
 * @param timePeriod The time period to filter by
 * @param requestStatus The status of the dismissal requests to filter on
 * @param repositoryName Filter dismissal requests by repository name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/list-org-dismissal-requests/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListOrgDismissalRequestsFilter(
        @Nullable String reviewer,
        @Nullable String requester,
        @Nullable GitHubAlertDismissalTimePeriod timePeriod,
        @Nullable GitHubAlertDismissalRequestStatus requestStatus,
        @Nullable String repositoryName) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListOrgDismissalRequestsFilter empty() {
        return GitHubListOrgDismissalRequestsFilterBuilder.builder().build();
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
        if (repositoryName != null) {
            query.put("repository_name", repositoryName);
        }
        return Collections.unmodifiableMap(query);
    }
}
