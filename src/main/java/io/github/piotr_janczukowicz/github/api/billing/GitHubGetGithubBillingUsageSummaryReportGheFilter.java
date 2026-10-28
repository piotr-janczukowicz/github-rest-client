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
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/usage/summary}, excluding pagination.
 *
 * @param year Year of the usage report.
 * @param month Month of the usage report.
 * @param day Day of the usage report.
 * @param organization The organization name to query usage for.
 * @param repository The repository name to query for usage in the format owner/repository.
 * @param product The product name to query usage for.
 * @param sku The SKU to query for usage.
 * @param costCenterId The ID corresponding to a cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-billing-usage-summary-report-ghe/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetGithubBillingUsageSummaryReportGheFilter(
        @Nullable Integer year,
        @Nullable Integer month,
        @Nullable Integer day,
        @Nullable String organization,
        @Nullable String repository,
        @Nullable String product,
        @Nullable String sku,
        @Nullable String costCenterId) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetGithubBillingUsageSummaryReportGheFilter empty() {
        return GitHubGetGithubBillingUsageSummaryReportGheFilterBuilder.builder()
                .build();
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
        if (organization != null) {
            query.put("organization", organization);
        }
        if (repository != null) {
            query.put("repository", repository);
        }
        if (product != null) {
            query.put("product", product);
        }
        if (sku != null) {
            query.put("sku", sku);
        }
        if (costCenterId != null) {
            query.put("cost_center_id", costCenterId);
        }
        return Collections.unmodifiableMap(query);
    }
}
