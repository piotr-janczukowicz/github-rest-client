package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Actions metrics that are included in the server statistics payload.
 *
 * @param numberOfReposUsingActions The total number of repositories in a GHES installation that have Actions enabled
 * @param percentageOfReposUsingActions The percentage of repositories in a GHES installation that have Actions enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics-actions")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsActions(
        @Nullable Integer numberOfReposUsingActions,
        @Nullable String percentageOfReposUsingActions) {}
