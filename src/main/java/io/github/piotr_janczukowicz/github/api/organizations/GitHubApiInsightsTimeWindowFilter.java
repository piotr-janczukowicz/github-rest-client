package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.DateTimeUtils.toIso;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for API Insights time-window endpoints.
 *
 * @param minTimestamp The minimum timestamp to query for stats, sent as {@code YYYY-MM-DDTHH:MM:SSZ}
 * @param maxTimestamp The maximum timestamp to query for stats
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/api-insights/get-summary-stats/parameters",
    "operations/api-insights/get-summary-stats-by-user/parameters",
    "operations/api-insights/get-summary-stats-by-actor/parameters"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsTimeWindowFilter(
        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp")
        Instant minTimestamp,

        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp") @Nullable Instant maxTimestamp) {

    public GitHubApiInsightsTimeWindowFilter {
        requireNonNull(minTimestamp, "minTimestamp");
    }

    /**
     * Filter with only {@code min_timestamp} set.
     *
     * @param minTimestamp minimum timestamp
     * @return filter
     */
    public static GitHubApiInsightsTimeWindowFilter of(final Instant minTimestamp) {
        return new GitHubApiInsightsTimeWindowFilter(minTimestamp, null);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (maxTimestamp == null) {
            return Map.of("min_timestamp", toIso(minTimestamp));
        }

        return Map.of("min_timestamp", toIso(minTimestamp), "max_timestamp", toIso(maxTimestamp));
    }
}
