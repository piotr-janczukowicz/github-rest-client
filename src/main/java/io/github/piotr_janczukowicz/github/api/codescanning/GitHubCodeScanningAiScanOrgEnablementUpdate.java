package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The AI Scan organization setting to apply.
 *
 * @param prScan Whether AI Scan is enabled for the organization. Organization respects enterprise policy. Disabled
 *     organizations prevent repositories from enabling AI Scan. Enabled organizations enable AI Scan for their
 *     repositories, but individual repositories can opt out.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-ai-scan-org-enablement-update")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAiScanOrgEnablementUpdate(GitHubCodeScanningAiScanPrScan prScan) {
    public GitHubCodeScanningAiScanOrgEnablementUpdate {
        requireNonNull(prScan, "prScan");
    }
}
