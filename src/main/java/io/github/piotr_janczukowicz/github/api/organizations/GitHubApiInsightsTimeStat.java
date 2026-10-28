package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * API Insights time stat for an organization.
 *
 * @param timestamp Timestamp
 * @param totalRequestCount The total number of requests within the queried time period
 * @param rateLimitedRequestCount The total number of requests that were rate limited within the queried time period
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-insights-time-stats/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsTimeStat(
        @Nullable String timestamp,
        @Nullable Long totalRequestCount,
        @Nullable Long rateLimitedRequestCount) {}
