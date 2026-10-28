package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/jobs}, excluding pagination.
 *
 * @param filter Filters jobs by their completed_at timestamp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/list-jobs-for-workflow-run/parameters")
@RecordBuilder
public record GitHubListJobsForWorkflowRunFilter(@Nullable GitHubJobFilterMode filter) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListJobsForWorkflowRunFilter empty() {
        return GitHubListJobsForWorkflowRunFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (filter == null) {
            return Map.of();
        }

        return Map.of("filter", filter.wireValue());
    }
}
