package io.github.piotr_janczukowicz.github.api.billing;

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
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/usage}, excluding pagination.
 *
 * @param year Year of the usage report. Four digits, for example 2025.
 * @param month Month of the usage report, between 1 and 12.
 * @param day Day of the usage report, between 1 and 31.
 * @param costCenterId The ID corresponding to a cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-billing-usage-report-ghe/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetGithubBillingUsageReportGheFilter(
        @Nullable Integer year,
        @Nullable Integer month,
        @Nullable Integer day,
        @Nullable String costCenterId) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetGithubBillingUsageReportGheFilter empty() {
        return GitHubGetGithubBillingUsageReportGheFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (year != null) {
            query.put("year", year.toString());
        }
        if (month != null) {
            query.put("month", month.toString());
        }
        if (day != null) {
            query.put("day", day.toString());
        }
        if (costCenterId != null) {
            query.put("cost_center_id", costCenterId);
        }
        return Collections.unmodifiableMap(query);
    }
}
