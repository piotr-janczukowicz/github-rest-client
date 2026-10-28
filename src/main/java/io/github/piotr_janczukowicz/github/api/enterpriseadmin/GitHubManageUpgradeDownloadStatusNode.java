package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageUpgradeDownloadStatusNode
 *
 * @param hostname The hostname of the node.
 * @param uuid The UUID of the node.
 * @param downloadId The download request identifier.
 * @param packageName The upgrade package filename.
 * @param status The per-node download status. One of: `not started`, `downloading`, `completed`, `failed`.
 * @param progress A progress indicator (e.g. `50%`).
 * @param error An error message if the node download failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-download-status-response/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageUpgradeDownloadStatusNode(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String downloadId,
        @Nullable String packageName,
        @Nullable GitHubManageUpgradeDownloadStatusNodeStatus status,
        @Nullable String progress,
        @Nullable String error) {}
