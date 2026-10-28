package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Gist counts.
 *
 * @param totalGists Total gists
 * @param privateGists Private gists
 * @param publicGists Public gists
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/gists")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsGists(
        @Nullable Integer totalGists,
        @Nullable Integer privateGists,
        @Nullable Integer publicGists) {}
