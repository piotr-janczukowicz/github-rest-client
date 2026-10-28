package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfill
 *
 * @param total total
 * @param totalPending total pending
 * @param totalProcessed total processed
 * @param totalFailed total failed
 * @param totalEligible total eligible
 * @param totalAcknowledged total acknowledged
 * @param breakdown breakdown
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/target_state/properties/repository_summaries/items/properties/backfill")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfill(
        long total,
        long totalPending,
        long totalProcessed,
        long totalFailed,
        long totalEligible,
        long totalAcknowledged,
        List<GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown> breakdown) {
    public GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfill {
        requireNonNull(breakdown, "breakdown");
    }
}
