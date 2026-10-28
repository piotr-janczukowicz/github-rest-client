package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown
 *
 * @param state state
 * @param kind kind
 * @param type type
 * @param count count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/target_state/properties/repository_summaries/items/properties/backfill/properties/breakdown/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown(
        String state, String kind, String type, long count) {
    public GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown {
        requireNonNull(state, "state");
        requireNonNull(kind, "kind");
        requireNonNull(type, "type");
    }
}
