package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * AI Scan enablement for a repository.
 *
 * @param prScan Whether AI Scan on pull requests is enabled for the repository.
 * @param repositoryScan Whether the full repository AI Scan is enabled for the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-ai-scan-enablement")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAiScanEnablement(
        GitHubCodeScanningAiScanPrScan prScan, @Nullable GitHubCodeScanningAiScanPrScan repositoryScan) {
    public GitHubCodeScanningAiScanEnablement {
        requireNonNull(prScan, "prScan");
    }
}
