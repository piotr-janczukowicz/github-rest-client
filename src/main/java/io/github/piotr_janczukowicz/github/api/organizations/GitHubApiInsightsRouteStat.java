package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * API Insights route stat for an actor.
 *
 * @param httpMethod The HTTP method
 * @param apiRoute The API path's route template
 * @param totalRequestCount The total number of requests within the queried time period
 * @param rateLimitedRequestCount The total number of requests that were rate limited within the queried time period
 * @param lastRateLimitedTimestamp Last rate limited timestamp
 * @param lastRequestTimestamp Last request timestamp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-insights-route-stats/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsRouteStat(
        @Nullable String httpMethod,
        @Nullable String apiRoute,
        @Nullable Long totalRequestCount,
        @Nullable Long rateLimitedRequestCount,
        @Nullable String lastRateLimitedTimestamp,
        @Nullable String lastRequestTimestamp) {}
