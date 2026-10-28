package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubManageDownloadUpgradeOrchestration
 *
 * @param message message
 * @param downloadId Gateway-generated download identifier for the orchestration.
 * @param maxParallel Effective maximum number of nodes that the gateway will download to at the same time.
 * @param totalNodes Number of targeted nodes in the orchestration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-download-manage-upgrade-orchestration-response")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageDownloadUpgradeOrchestration(
        String message, String downloadId, int maxParallel, int totalNodes)
        implements GitHubManageDownloadUpgradeResult {
    public GitHubManageDownloadUpgradeOrchestration {
        requireNonNull(message, "message");
        requireNonNull(downloadId, "downloadId");
    }
}
