package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * User counts.
 *
 * @param totalUsers Total users
 * @param adminUsers Site administrators
 * @param suspendedUsers Suspended users
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/users")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsUsers(
        @Nullable Integer totalUsers,
        @Nullable Integer adminUsers,
        @Nullable Integer suspendedUsers) {}
