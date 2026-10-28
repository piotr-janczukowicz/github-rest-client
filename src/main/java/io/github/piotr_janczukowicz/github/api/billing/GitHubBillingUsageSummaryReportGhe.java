package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Billing usage summary for an enterprise.
 *
 * @param timePeriod Time period of the report.
 * @param enterprise The name of the enterprise for the usage report.
 * @param organization The name of the organization for the usage report.
 * @param repository The name of the repository for the usage report.
 * @param product The product for the usage report.
 * @param sku The SKU for the usage report.
 * @param costCenter Cost center of the report.
 * @param usageItems Usage items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-usage-summary-report-ghe")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingUsageSummaryReportGhe(
        @JsonProperty("timePeriod") GitHubBillingUsageSummaryReportGheTimePeriod timePeriod,
        String enterprise,
        @Nullable String organization,
        @Nullable String repository,
        @Nullable String product,
        @Nullable String sku,
        @JsonProperty("costCenter") @Nullable GitHubBillingUsageSummaryReportGheCostCenter costCenter,
        @JsonProperty("usageItems") List<GitHubBillingUsageSummaryReportGheUsageItem> usageItems) {
    public GitHubBillingUsageSummaryReportGhe {
        requireNonNull(timePeriod, "timePeriod");
        requireNonNull(enterprise, "enterprise");
        requireNonNull(usageItems, "usageItems");
    }
}
