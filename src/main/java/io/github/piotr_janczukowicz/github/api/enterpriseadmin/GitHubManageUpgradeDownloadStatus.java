package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageUpgradeDownloadStatus
 *
 * @param downloadId The unique identifier for the download request.
 * @param packageName The filename of the upgrade package.
 * @param status Overall download status. One of: `not started`, `downloading`, `completed`, `failed`.
 * @param errorMessage An error message if the download failed.
 * @param nodes Per-node download status details.
 * @param orchestration Gateway-side orchestration status. Present while a throttled orchestration is active, or while a
 *     terminal orchestration snapshot is still retained.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-download-status-response")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageUpgradeDownloadStatus(
        @Nullable String downloadId,
        @Nullable String packageName,
        @Nullable GitHubManageUpgradeDownloadStatusStatus status,
        @Nullable String errorMessage,
        @Nullable List<GitHubManageUpgradeDownloadStatusNode> nodes,
        @Nullable GitHubManageUpgradeDownloadStatusOrchestration orchestration) {}
