package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * API Insights subject stat for an organization.
 *
 * @param subjectType Subject type
 * @param subjectName Subject name
 * @param subjectId Subject id
 * @param totalRequestCount Total request count
 * @param rateLimitedRequestCount Rate limited request count
 * @param lastRateLimitedTimestamp Last rate limited timestamp
 * @param lastRequestTimestamp Last request timestamp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-insights-subject-stats/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsSubjectStat(
        @Nullable String subjectType,
        @Nullable String subjectName,
        @Nullable Long subjectId,
        @Nullable Integer totalRequestCount,
        @Nullable Integer rateLimitedRequestCount,
        @Nullable String lastRateLimitedTimestamp,
        @Nullable String lastRequestTimestamp) {}
