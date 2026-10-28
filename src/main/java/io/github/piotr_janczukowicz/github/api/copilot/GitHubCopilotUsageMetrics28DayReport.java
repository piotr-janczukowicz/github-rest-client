package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import java.util.List;

/**
 * Links to download the latest Copilot usage metrics report for an enterprise/organization.
 *
 * @param downloadLinks The URLs to download the latest Copilot usage metrics report for the enterprise/organization.
 * @param reportStartDay The start date of the report period in {@code YYYY-MM-DD} format.
 * @param reportEndDay The end date of the report period in {@code YYYY-MM-DD} format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-usage-metrics-28-day-report")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotUsageMetrics28DayReport(
        List<String> downloadLinks, LocalDate reportStartDay, LocalDate reportEndDay) {

    public GitHubCopilotUsageMetrics28DayReport {
        requireNonNull(downloadLinks, "downloadLinks");
        requireNonNull(reportStartDay, "reportStartDay");
        requireNonNull(reportEndDay, "reportEndDay");
    }
}
