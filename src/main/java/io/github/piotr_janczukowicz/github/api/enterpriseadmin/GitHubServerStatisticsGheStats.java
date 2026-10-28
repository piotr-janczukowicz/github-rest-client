package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Aggregate statistics of a server installation.
 *
 * @param comments Comment statistics
 * @param gists Gist statistics
 * @param hooks Webhook statistics
 * @param issues Issue statistics
 * @param milestones Milestone statistics
 * @param orgs Organization statistics
 * @param pages Pages statistics
 * @param pulls Pull request statistics
 * @param repos Repository statistics
 * @param users User statistics
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStats(
        @Nullable GitHubServerStatisticsGheStatsComments comments,
        @Nullable GitHubServerStatisticsGheStatsGists gists,
        @Nullable GitHubServerStatisticsGheStatsHooks hooks,
        @Nullable GitHubServerStatisticsGheStatsIssues issues,
        @Nullable GitHubServerStatisticsGheStatsMilestones milestones,
        @Nullable GitHubServerStatisticsGheStatsOrgs orgs,
        @Nullable GitHubServerStatisticsGheStatsPages pages,
        @Nullable GitHubServerStatisticsGheStatsPulls pulls,
        @Nullable GitHubServerStatisticsGheStatsRepos repos,
        @Nullable GitHubServerStatisticsGheStatsUsers users) {}
