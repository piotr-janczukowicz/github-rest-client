package io.github.piotr_janczukowicz.github.api.dependabot;

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
 * Query filters for {@code GET /orgs/{org}/dismissal-requests/dependabot}, excluding pagination.
 *
 * @param repositoryName Filter dismissal requests by repository name
 * @param reviewer Filter dismissal requests by the handle of the GitHub user who reviewed the request
 * @param requester Filter dismissal requests by the handle of the GitHub user who requested the dismissal
 * @param timePeriod The time period to filter by
 * @param requestStatus The status of the dismissal requests to filter on
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/list-dismissal-requests-for-org/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListDismissalRequestsForOrgFilter(
        @Nullable String repositoryName,
        @Nullable String reviewer,
        @Nullable String requester,
        @Nullable GitHubRuleSuiteTimePeriod timePeriod,
        @Nullable GitHubDismissalRequestStatus requestStatus) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListDismissalRequestsForOrgFilter empty() {
        return GitHubListDismissalRequestsForOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (repositoryName != null) {
            query.put("repository_name", repositoryName);
        }
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
