package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/issues/{issue_number}/timeline}, excluding pagination.
 *
 * @param exclude comma-separated timeline event names to exclude
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-events-for-timeline/parameters")
@RecordBuilder
public record GitHubListEventsForTimelineFilter(@Nullable String exclude) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListEventsForTimelineFilter empty() {
        return GitHubListEventsForTimelineFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (exclude == null) {
            return Map.of();
        }

        return Map.of("exclude", exclude);
    }
}
