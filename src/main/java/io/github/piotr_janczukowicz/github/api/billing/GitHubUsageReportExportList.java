package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Usage report exports of an enterprise.
 *
 * @param usageReportExports List of usage report exports.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/usage-report-export-list")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUsageReportExportList(List<GitHubUsageReportExport> usageReportExports) {
    public GitHubUsageReportExportList {
        requireNonNull(usageReportExports, "usageReportExports");
    }
}
