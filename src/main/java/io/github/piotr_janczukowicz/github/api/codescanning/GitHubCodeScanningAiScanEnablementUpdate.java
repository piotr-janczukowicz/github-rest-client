package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * AI Scan enablement update for a repository.
 *
 * @param prScan Whether to enable or disable AI Scan on pull requests for the repository.
 * @param repositoryScan Whether to enable or disable the full repository AI Scan for the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-ai-scan-enablement-update")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAiScanEnablementUpdate(
        @Nullable GitHubCodeScanningAiScanPrScan prScan,
        @Nullable GitHubCodeScanningAiScanPrScan repositoryScan) {
    public GitHubCodeScanningAiScanEnablementUpdate {
        if (prScan == null && repositoryScan == null) {
            throw new IllegalArgumentException("at least one of prScan or repositoryScan is required");
        }
    }
}
