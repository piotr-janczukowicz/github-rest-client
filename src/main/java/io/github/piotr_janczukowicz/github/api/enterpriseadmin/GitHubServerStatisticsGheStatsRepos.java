package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Repository counts.
 *
 * @param totalRepos Total repositories
 * @param rootRepos Root repositories
 * @param forkRepos Fork repositories
 * @param orgRepos Organization repositories
 * @param totalPushes Total pushes
 * @param totalWikis Total wikis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/repos")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsRepos(
        @Nullable Integer totalRepos,
        @Nullable Integer rootRepos,
        @Nullable Integer forkRepos,
        @Nullable Integer orgRepos,
        @Nullable Integer totalPushes,
        @Nullable Integer totalWikis) {}
