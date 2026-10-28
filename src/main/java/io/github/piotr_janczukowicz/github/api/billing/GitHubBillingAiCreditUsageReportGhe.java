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
 * Billing AI credit usage report for an enterprise.
 *
 * @param timePeriod Time period of the report.
 * @param enterprise The name of the enterprise for the usage report.
 * @param user The name of the user for the usage report.
 * @param organization The name of the organization for the usage report.
 * @param product The product for the usage report.
 * @param model The model for the usage report.
 * @param costCenter Cost center of the report.
 * @param usageItems Usage items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-ai-credit-usage-report-ghe")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingAiCreditUsageReportGhe(
        @JsonProperty("timePeriod") GitHubBillingAiCreditUsageReportGheTimePeriod timePeriod,
        String enterprise,
        @Nullable String user,
        @Nullable String organization,
        @Nullable String product,
        @Nullable String model,
        @JsonProperty("costCenter") @Nullable GitHubBillingAiCreditUsageReportGheCostCenter costCenter,
        @JsonProperty("usageItems") List<GitHubBillingAiCreditUsageReportGheUsageItem> usageItems) {
    public GitHubBillingAiCreditUsageReportGhe {
        requireNonNull(timePeriod, "timePeriod");
        requireNonNull(enterprise, "enterprise");
        requireNonNull(usageItems, "usageItems");
    }
}
