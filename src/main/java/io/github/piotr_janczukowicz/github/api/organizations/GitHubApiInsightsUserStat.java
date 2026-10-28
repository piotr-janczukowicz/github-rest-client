package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * API Insights user stat for a user.
 *
 * @param actorType Actor type
 * @param actorName Actor name
 * @param actorId Actor id
 * @param integrationId Integration id
 * @param oauthApplicationId OAuth application id
 * @param totalRequestCount Total request count
 * @param rateLimitedRequestCount Rate limited request count
 * @param lastRateLimitedTimestamp Last rate limited timestamp
 * @param lastRequestTimestamp Last request timestamp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-insights-user-stats/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsUserStat(
        @Nullable String actorType,
        @Nullable String actorName,
        @Nullable Long actorId,
        @Nullable Long integrationId,
        @Nullable Long oauthApplicationId,
        @Nullable Integer totalRequestCount,
        @Nullable Integer rateLimitedRequestCount,
        @Nullable String lastRateLimitedTimestamp,
        @Nullable String lastRequestTimestamp) {}
