package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Milestone counts.
 *
 * @param totalMilestones Total milestones
 * @param openMilestones Open milestones
 * @param closedMilestones Closed milestones
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/milestones")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsMilestones(
        @Nullable Integer totalMilestones,
        @Nullable Integer openMilestones,
        @Nullable Integer closedMilestones) {}
