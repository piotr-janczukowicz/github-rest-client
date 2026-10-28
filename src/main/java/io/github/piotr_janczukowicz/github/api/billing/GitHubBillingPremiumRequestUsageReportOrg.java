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
 * Billing premium request usage report for a organization.
 *
 * @param timePeriod Time Period.
 * @param organization The unique identifier of the organization.
 * @param user The name of the user for the usage report.
 * @param product The product for the usage report.
 * @param model The model for the usage report.
 * @param usageItems Usage Items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-premium-request-usage-report-org")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubBillingPremiumRequestUsageReportOrg(
        @JsonProperty("timePeriod") GitHubBillingPremiumRequestUsageReportOrgTimePeriod timePeriod,
        String organization,
        @Nullable String user,
        @Nullable String product,
        @Nullable String model,
        @JsonProperty("usageItems") List<GitHubBillingPremiumRequestUsageReportOrgUsageItem> usageItems) {

    public GitHubBillingPremiumRequestUsageReportOrg {
        requireNonNull(timePeriod, "timePeriod");
        requireNonNull(organization, "organization");
        requireNonNull(usageItems, "usageItems");
    }
}
