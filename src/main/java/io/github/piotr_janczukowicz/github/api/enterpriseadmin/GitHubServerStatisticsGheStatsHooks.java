package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Webhook counts.
 *
 * @param totalHooks Total webhooks
 * @param activeHooks Active webhooks
 * @param inactiveHooks Inactive webhooks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/hooks")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsHooks(
        @Nullable Integer totalHooks,
        @Nullable Integer activeHooks,
        @Nullable Integer inactiveHooks) {}
