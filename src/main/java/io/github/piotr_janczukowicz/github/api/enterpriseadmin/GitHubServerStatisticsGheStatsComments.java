package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Comment counts.
 *
 * @param totalCommitComments Total commit comments
 * @param totalGistComments Total gist comments
 * @param totalIssueComments Total issue comments
 * @param totalPullRequestComments Total pull request comments
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/ghe_stats/properties/comments")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsGheStatsComments(
        @Nullable Integer totalCommitComments,
        @Nullable Integer totalGistComments,
        @Nullable Integer totalIssueComments,
        @Nullable Integer totalPullRequestComments) {}
