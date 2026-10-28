package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.DateTimeUtils.toIso;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/insights/api/route-stats/{actor_type}/{actor_id}}, excluding pagination.
 *
 * @param minTimestamp The minimum timestamp to query for stats, sent as {@code YYYY-MM-DDTHH:MM:SSZ}
 * @param maxTimestamp The maximum timestamp to query for stats
 * @param direction The direction to sort the results by
 * @param sort The properties to sort the results by
 * @param apiRouteSubstring Substring filter for API route
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/api-insights/get-route-stats-by-actor/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsRouteStatsFilter(
        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp")
        Instant minTimestamp,

        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp") @Nullable Instant maxTimestamp,

        @Nullable GitHubDirection direction,
        @Nullable List<GitHubApiInsightsRouteStatsSort> sort,
        @Nullable String apiRouteSubstring) {

    public GitHubApiInsightsRouteStatsFilter {
        requireNonNull(minTimestamp, "minTimestamp");
        if (sort != null) {
            sort = List.copyOf(sort);
        }
    }

    /**
     * Filter with only {@code min_timestamp} set.
     *
     * @param minTimestamp minimum timestamp
     * @return filter
     */
    public static GitHubApiInsightsRouteStatsFilter of(final Instant minTimestamp) {
        return new GitHubApiInsightsRouteStatsFilter(minTimestamp, null, null, null, null);
    }

    /**
     * Non-null query parameters for this filter. Sort values are comma-separated.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        query.put("min_timestamp", toIso(minTimestamp));
        if (maxTimestamp != null) {
            query.put("max_timestamp", toIso(maxTimestamp));
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (sort != null && !sort.isEmpty()) {
            query.put(
                    "sort",
                    String.join(
                            ",",
                            sort.stream()
                                    .map(GitHubApiInsightsRouteStatsSort::wireValue)
                                    .toList()));
        }
        if (apiRouteSubstring != null) {
            query.put("api_route_substring", apiRouteSubstring);
        }
        return Collections.unmodifiableMap(query);
    }
}
