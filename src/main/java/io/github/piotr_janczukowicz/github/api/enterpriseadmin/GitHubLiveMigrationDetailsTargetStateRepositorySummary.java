package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationDetailsTargetStateRepositorySummary
 *
 * @param namespace namespace
 * @param repository repository
 * @param total total
 * @param totalBackfill total backfill
 * @param totalLiveUpdate total live update
 * @param totalProcessed total processed
 * @param totalFailed total failed
 * @param totalPending total pending
 * @param totalEligible total eligible
 * @param totalAcknowledged total acknowledged
 * @param initialGitPushComplete initial git push complete
 * @param backfill backfill
 * @param liveUpdate live update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/target_state/properties/repository_summaries/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsTargetStateRepositorySummary(
        String namespace,
        String repository,
        long total,
        long totalBackfill,
        long totalLiveUpdate,
        long totalProcessed,
        long totalFailed,
        long totalPending,
        long totalEligible,
        long totalAcknowledged,
        boolean initialGitPushComplete,
        @Nullable GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfill backfill,
        @Nullable GitHubLiveMigrationDetailsTargetStateRepositorySummaryLiveUpdate liveUpdate) {
    public GitHubLiveMigrationDetailsTargetStateRepositorySummary {
        requireNonNull(namespace, "namespace");
        requireNonNull(repository, "repository");
    }
}
