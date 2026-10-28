package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/runs/{run_id}}, excluding pagination.
 *
 * @param excludePullRequests If {@code true}, pull requests are excluded from the results
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/get-workflow-run/parameters")
@RecordBuilder
public record GitHubGetWorkflowRunFilter(@Nullable Boolean excludePullRequests) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetWorkflowRunFilter empty() {
        return GitHubGetWorkflowRunFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (excludePullRequests == null) {
            return Map.of();
        }

        return Map.of("exclude_pull_requests", excludePullRequests.toString());
    }
}
