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
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/ai_credit/usage}, excluding pagination.
 *
 * @param year Year of the usage report.
 * @param month Month of the usage report.
 * @param day Day of the usage report.
 * @param organization The organization name to query usage for.
 * @param user The user name to query usage for.
 * @param model The model name to query usage for.
 * @param product The product name to query usage for.
 * @param costCenterId The ID corresponding to a cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-billing-ai-credit-usage-report-ghe/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetGithubBillingAiCreditUsageReportGheFilter(
        @Nullable Integer year,
        @Nullable Integer month,
        @Nullable Integer day,
        @Nullable String organization,
        @Nullable String user,
        @Nullable String model,
        @Nullable String product,
        @Nullable String costCenterId) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetGithubBillingAiCreditUsageReportGheFilter empty() {
        return GitHubGetGithubBillingAiCreditUsageReportGheFilterBuilder.builder()
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
        if (user != null) {
            query.put("user", user);
        }
        if (model != null) {
            query.put("model", model);
        }
        if (product != null) {
            query.put("product", product);
        }
        if (costCenterId != null) {
            query.put("cost_center_id", costCenterId);
        }
        return Collections.unmodifiableMap(query);
    }
}
