package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Organization counts.
 *
 * @param totalOrgs Total organizations
 * @param disabledOrgs Disabled organizations
 * @param totalTeams Total teams
 * @param totalTeamMembers Total team members
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/orgs")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsOrgs(
        @Nullable Integer totalOrgs,
        @Nullable Integer disabledOrgs,
        @Nullable Integer totalTeams,
        @Nullable Integer totalTeamMembers) {}
