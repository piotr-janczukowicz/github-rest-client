package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Billing usage report for a user.
 *
 * @param usageItems Usage Items.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-usage-report-user")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubBillingUsageReportUser(
        @JsonProperty("usageItems") @Nullable List<GitHubBillingUsageReportUserUsageItem> usageItems) {}
