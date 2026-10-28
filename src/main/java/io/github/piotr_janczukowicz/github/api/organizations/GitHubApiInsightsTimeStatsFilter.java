package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.DateTimeUtils.toIso;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for API Insights time-stats endpoints.
 *
 * @param minTimestamp The minimum timestamp to query for stats, sent as {@code YYYY-MM-DDTHH:MM:SSZ}
 * @param maxTimestamp The maximum timestamp to query for stats
 * @param timestampIncrement The increment of time used to breakdown the query results (5m, 10m, 1h, etc.)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/api-insights/get-time-stats/parameters",
    "operations/api-insights/get-time-stats-by-user/parameters",
    "operations/api-insights/get-time-stats-by-actor/parameters"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsTimeStatsFilter(
        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp")
        Instant minTimestamp,

        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp") @Nullable Instant maxTimestamp,

        String timestampIncrement) {

    public GitHubApiInsightsTimeStatsFilter {
        requireNonNull(minTimestamp, "minTimestamp");
        requireNotBlank(timestampIncrement, "timestampIncrement");
    }

    /**
     * Filter with {@code min_timestamp} and {@code timestamp_increment} set.
     *
     * @param minTimestamp minimum timestamp
     * @param timestampIncrement timestamp increment for bucketing
     * @return filter
     */
    public static GitHubApiInsightsTimeStatsFilter of(final Instant minTimestamp, final String timestampIncrement) {
        return new GitHubApiInsightsTimeStatsFilter(minTimestamp, null, timestampIncrement);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        query.put("min_timestamp", toIso(minTimestamp));
        if (maxTimestamp != null) {
            query.put("max_timestamp", toIso(maxTimestamp));
        }
        query.put("timestamp_increment", timestampIncrement);
        return Collections.unmodifiableMap(query);
    }
}
