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
 * Links to download the Copilot usage metrics report for an enterprise/organization for a specific day.
 *
 * @param downloadLinks The URLs to download the Copilot usage metrics report for the enterprise/organization for the
 *     specified day.
 * @param reportDay The day of the report in {@code YYYY-MM-DD} format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-usage-metrics-1-day-report")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotUsageMetrics1DayReport(List<String> downloadLinks, LocalDate reportDay) {

    public GitHubCopilotUsageMetrics1DayReport {
        requireNonNull(downloadLinks, "downloadLinks");
        requireNonNull(reportDay, "reportDay");
    }
}
