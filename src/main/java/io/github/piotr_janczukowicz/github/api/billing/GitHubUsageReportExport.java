package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Usage report export of an enterprise.
 *
 * @param id Unique identifier for the usage report export.
 * @param reportType The type of usage report.
 * @param startDate The start date for the report.
 * @param endDate The end date for the report.
 * @param status The current status of the report export.
 * @param downloadUrls URLs to download the completed report. They may embed a signature: do not log them or send
 *     credentials to them
 * @param createdAt When the report export was created.
 * @param actor The login of the user who requested the export.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/usage-report-export")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUsageReportExport(
        String id,
        GitHubUsageReportExportReportType reportType,
        LocalDate startDate,
        LocalDate endDate,
        GitHubUsageReportExportStatus status,
        @Nullable List<String> downloadUrls,
        @Nullable Instant createdAt,
        @Nullable String actor) {
    public GitHubUsageReportExport {
        requireNonNull(id, "id");
        requireNonNull(reportType, "reportType");
        requireNonNull(startDate, "startDate");
        requireNonNull(endDate, "endDate");
        requireNonNull(status, "status");
    }
}
