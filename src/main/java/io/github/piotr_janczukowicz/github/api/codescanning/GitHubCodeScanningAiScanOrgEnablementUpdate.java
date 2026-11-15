package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The AI Scan organization setting to apply.
 *
 * @param prScan Whether AI Scan on pull requests is enabled for the organization. Organization respects enterprise
 *     policy. Disabled organizations prevent repositories from enabling AI Scan. Enabled organizations enable AI Scan
 *     for their repositories, but individual repositories can opt out.
 * @param repositoryScan Whether the full repository AI Scan is enabled for the organization. Organization respects
 *     enterprise policy. Disabled organizations prevent repositories from enabling the full repository AI Scan. Enabled
 *     organizations enable it for their repositories, but individual repositories can opt out.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-ai-scan-org-enablement-update")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAiScanOrgEnablementUpdate(
        @Nullable GitHubCodeScanningAiScanPrScan prScan,
        @Nullable GitHubCodeScanningAiScanPrScan repositoryScan) {
    public GitHubCodeScanningAiScanOrgEnablementUpdate {
        if (prScan == null && repositoryScan == null) {
            throw new IllegalArgumentException("at least one of prScan or repositoryScan is required");
        }
    }
}
