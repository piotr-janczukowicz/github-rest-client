package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.StringUtils.isNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/activity}, excluding cursor pagination.
 *
 * @param direction sort direction
 * @param ref Git reference filter
 * @param actor actor username filter
 * @param timePeriod time period filter
 * @param activityType activity type filter
 */
@CatalogSchema("operations/repos/list-activities/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubListActivitiesFilter(
        @Nullable GitHubReposSortDirection direction,
        @Nullable String ref,
        @Nullable String actor,
        @Nullable GitHubRepositoryActivityTimePeriod timePeriod,
        @Nullable GitHubRepositoryActivityType activityType) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListActivitiesFilter empty() {
        return GitHubListActivitiesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (isNotBlank(ref)) {
            query.put("ref", ref);
        }
        if (isNotBlank(actor)) {
            query.put("actor", actor);
        }
        if (timePeriod != null) {
            query.put("time_period", timePeriod.wireValue());
        }
        if (activityType != null) {
            query.put("activity_type", activityType.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
