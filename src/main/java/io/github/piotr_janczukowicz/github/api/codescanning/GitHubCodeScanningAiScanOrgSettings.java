package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The AI Scan organization setting.
 *
 * @param prScan Whether AI Scan on pull requests is enabled for the organization. The organization setting respects
 *     enterprise policy, and repositories inherit it: when disabled, repositories cannot enable AI Scan; when enabled,
 *     repositories can still opt out.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-ai-scan-org-settings")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningAiScanOrgSettings(GitHubCodeScanningAiScanPrScan prScan) {
    public GitHubCodeScanningAiScanOrgSettings {
        requireNonNull(prScan, "prScan");
    }
}
