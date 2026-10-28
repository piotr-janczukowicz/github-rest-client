package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Gateway-side orchestration status. Present while a throttled orchestration is active, or while a terminal
 * orchestration snapshot is still retained.
 *
 * @param phase Gateway orchestration phase. One of: `running`, `completed`, `failed`.
 * @param errorMessage Error message for the orchestration when it failed.
 * @param maxParallel Effective maximum number of nodes downloading at the same time.
 * @param queued Number of targeted nodes waiting to start.
 * @param inFlight Number of targeted nodes currently downloading.
 * @param completed Number of targeted nodes that completed.
 * @param failed Number of targeted nodes that failed.
 * @param total Total number of targeted nodes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-download-status-response/properties/orchestration")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageUpgradeDownloadStatusOrchestration(
        @Nullable GitHubManageUpgradeDownloadStatusOrchestrationPhase phase,
        @Nullable String errorMessage,
        @Nullable Integer maxParallel,
        @Nullable Integer queued,
        @Nullable Integer inFlight,
        @Nullable Integer completed,
        @Nullable Integer failed,
        @Nullable Integer total) {}
