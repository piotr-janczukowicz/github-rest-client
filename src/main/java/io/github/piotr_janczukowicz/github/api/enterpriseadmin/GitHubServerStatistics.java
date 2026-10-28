package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Statistics collected from a GitHub Enterprise Server installation.
 *
 * @param serverId The unique identifier of the server
 * @param collectionDate The date the statistics were collected
 * @param schemaVersion The schema version of the statistics payload
 * @param ghesVersion The GitHub Enterprise Server version
 * @param hostName The host name of the server
 * @param githubConnect GitHub Connect statistics
 * @param gheStats Statistics of the installation
 * @param dormantUsers Dormant user statistics
 * @param actionsStats Actions metrics
 * @param packagesStats Packages metrics
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatistics(
        @Nullable String serverId,
        @Nullable String collectionDate,
        @Nullable String schemaVersion,
        @Nullable String ghesVersion,
        @Nullable String hostName,
        @Nullable GitHubServerStatisticsGithubConnect githubConnect,
        @Nullable GitHubServerStatisticsGheStats gheStats,
        @Nullable GitHubServerStatisticsDormantUsers dormantUsers,
        @Nullable GitHubServerStatisticsActions actionsStats,
        @Nullable GitHubServerStatisticsPackages packagesStats) {}
