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
 * Query filters for {@code GET /organizations/{org}/settings/billing/usage/summary}, excluding pagination.
 *
 * @param year If specified, only return results for a single year. The value of `year` is an integer with four digits
 *     representing a year. For example, `2025`. Default value is the current year.
 * @param month If specified, only return results for a single month. The value of `month` is an integer between `1` and
 *     `12`. Default value is the current month. If no year is specified the default `year` is used.
 * @param day If specified, only return results for a single day. The value of `day` is an integer between `1` and `31`.
 *     If no `year` or `month` is specified, the default `year` and `month` are used.
 * @param repository The repository name to query for usage in the format owner/repository.
 * @param product The product name to query usage for. The name is not case sensitive.
 * @param sku The SKU to query for usage.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-billing-usage-summary-report-org/parameters")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubGetGithubBillingUsageSummaryReportOrgFilter(
        @Nullable Integer year,
        @Nullable Integer month,
        @Nullable Integer day,
        @Nullable String repository,
        @Nullable String product,
        @Nullable String sku) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetGithubBillingUsageSummaryReportOrgFilter empty() {
        return GitHubGetGithubBillingUsageSummaryReportOrgFilterBuilder.builder()
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

        if (repository != null) {
            query.put("repository", repository);
        }

        if (product != null) {
            query.put("product", product);
        }

        if (sku != null) {
            query.put("sku", sku);
        }

        return Collections.unmodifiableMap(query);
    }
}
