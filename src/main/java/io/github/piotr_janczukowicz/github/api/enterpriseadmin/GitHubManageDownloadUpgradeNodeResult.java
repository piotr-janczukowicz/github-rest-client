package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageDownloadUpgradeNodeResult
 *
 * @param hostname The hostname of the node.
 * @param uuid The UUID of the node.
 * @param downloadId Gateway-generated download identifier for the orchestration.
 * @param message A message indicating that the download has started.
 * @param error An error message if the request failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-download-manage-upgrade-response/items")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageDownloadUpgradeNodeResult(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String downloadId,
        @Nullable String message,
        @Nullable String error) {}
