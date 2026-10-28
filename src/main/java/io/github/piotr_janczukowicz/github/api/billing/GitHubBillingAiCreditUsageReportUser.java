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
 * Billing AI credit usage report for a user.
 *
 * @param timePeriod Time Period.
 * @param user The unique identifier of the user.
 * @param product The product for the usage report.
 * @param model The model for the usage report.
 * @param usageItems Usage Items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-ai-credit-usage-report-user")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubBillingAiCreditUsageReportUser(
        @JsonProperty("timePeriod") GitHubBillingAiCreditUsageReportUserTimePeriod timePeriod,
        String user,
        @Nullable String product,
        @Nullable String model,
        @JsonProperty("usageItems") List<GitHubBillingAiCreditUsageReportUserUsageItem> usageItems) {

    public GitHubBillingAiCreditUsageReportUser {
        requireNonNull(timePeriod, "timePeriod");
        requireNonNull(user, "user");
        requireNonNull(usageItems, "usageItems");
    }
}
