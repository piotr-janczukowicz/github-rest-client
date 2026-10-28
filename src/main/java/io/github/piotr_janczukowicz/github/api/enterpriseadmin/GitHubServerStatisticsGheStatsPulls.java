package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request counts.
 *
 * @param totalPulls Total pull requests
 * @param mergedPulls Merged pull requests
 * @param mergeablePulls Mergeable pull requests
 * @param unmergeablePulls Unmergeable pull requests
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/pulls")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsPulls(
        @Nullable Integer totalPulls,
        @Nullable Integer mergedPulls,
        @Nullable Integer mergeablePulls,
        @Nullable Integer unmergeablePulls) {}
