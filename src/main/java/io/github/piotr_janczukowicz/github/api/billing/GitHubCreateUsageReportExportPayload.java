package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Request body for requesting a usage report export.
 *
 * @param reportType The type of usage report to generate.
 * @param startDate The start date for the report.
 * @param endDate The end date for the report.
 * @param sendEmail Whether to send an email notification when the report is ready.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-usage-report-export/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateUsageReportExportPayload(
        GitHubCreateUsageReportExportPayloadReportType reportType,
        LocalDate startDate,
        @Nullable LocalDate endDate,
        @Nullable Boolean sendEmail) {
    public GitHubCreateUsageReportExportPayload {
        requireNonNull(reportType, "reportType");
        requireNonNull(startDate, "startDate");
    }
}
