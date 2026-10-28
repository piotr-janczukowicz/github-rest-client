package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue counts.
 *
 * @param totalIssues Total issues
 * @param openIssues Open issues
 * @param closedIssues Closed issues
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/issues")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsIssues(
        @Nullable Integer totalIssues,
        @Nullable Integer openIssues,
        @Nullable Integer closedIssues) {}
