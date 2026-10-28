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
 * Billing usage summary report for a user.
 *
 * @param timePeriod Time Period.
 * @param user The unique identifier of the user.
 * @param repository The name of the repository for the usage report.
 * @param product The product for the usage report.
 * @param sku The SKU for the usage report.
 * @param usageItems Usage Items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-usage-summary-report-user")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubBillingUsageSummaryReportUser(
        @JsonProperty("timePeriod") GitHubBillingUsageSummaryReportUserTimePeriod timePeriod,
        String user,
        @Nullable String repository,
        @Nullable String product,
        @Nullable String sku,
        @JsonProperty("usageItems") List<GitHubBillingUsageSummaryReportUserUsageItem> usageItems) {

    public GitHubBillingUsageSummaryReportUser {
        requireNonNull(timePeriod, "timePeriod");
        requireNonNull(user, "user");
        requireNonNull(usageItems, "usageItems");
    }
}
