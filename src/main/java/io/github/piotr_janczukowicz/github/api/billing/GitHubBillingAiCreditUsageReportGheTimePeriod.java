package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Time period covered by a billing usage report.
 *
 * @param year The year for the usage report.
 * @param month The month for the usage report.
 * @param day The day for the usage report.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-ai-credit-usage-report-ghe/properties/timePeriod")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingAiCreditUsageReportGheTimePeriod(
        int year, @Nullable Integer month, @Nullable Integer day) {}
