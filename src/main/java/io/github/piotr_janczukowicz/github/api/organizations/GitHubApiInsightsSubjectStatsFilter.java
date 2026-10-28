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
 * Query filters for {@code GET /orgs/{org}/insights/api/subject-stats}, excluding pagination.
 *
 * @param minTimestamp The minimum timestamp to query for stats, sent as {@code YYYY-MM-DDTHH:MM:SSZ}
 * @param maxTimestamp The maximum timestamp to query for stats
 * @param direction The direction to sort the results by
 * @param sort The properties to sort the results by
 * @param subjectNameSubstring Substring filter for subject name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/api-insights/get-subject-stats/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubApiInsightsSubjectStatsFilter(
        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp")
        Instant minTimestamp,

        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp") @Nullable Instant maxTimestamp,

        @Nullable GitHubDirection direction,
        @Nullable List<GitHubApiInsightsSort> sort,
        @Nullable String subjectNameSubstring) {

    public GitHubApiInsightsSubjectStatsFilter {
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
    public static GitHubApiInsightsSubjectStatsFilter of(final Instant minTimestamp) {
        return new GitHubApiInsightsSubjectStatsFilter(minTimestamp, null, null, null, null);
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
                            sort.stream().map(GitHubApiInsightsSort::wireValue).toList()));
        }
        if (subjectNameSubstring != null) {
            query.put("subject_name_substring", subjectNameSubstring);
        }
        return Collections.unmodifiableMap(query);
    }
}
