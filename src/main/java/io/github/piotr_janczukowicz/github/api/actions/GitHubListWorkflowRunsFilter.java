package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/workflows/{workflow_id}/runs}, excluding pagination.
 *
 * @param actor Returns someone's workflow runs, by the user who triggered the run
 * @param branch Returns workflow runs associated with a branch
 * @param event Returns workflow runs for a specific event type
 * @param status Returns workflow runs with the check run status or conclusion
 * @param created Returns workflow runs created within the given date-time range
 * @param excludePullRequests If {@code true}, pull requests are excluded from the results
 * @param checkSuiteId Returns workflow runs with the check suite ID
 * @param headSha Only returns workflow runs that are associated with the specified head SHA
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/list-workflow-runs/parameters")
@RecordBuilder
public record GitHubListWorkflowRunsFilter(
        @Nullable String actor,
        @Nullable String branch,
        @Nullable String event,
        @Nullable GitHubWorkflowRunStatus status,
        @Nullable Instant created,
        @Nullable Boolean excludePullRequests,
        @Nullable Long checkSuiteId,
        @Nullable String headSha) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListWorkflowRunsFilter empty() {
        return GitHubListWorkflowRunsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (actor != null) {
            query.put("actor", actor);
        }
        if (branch != null) {
            query.put("branch", branch);
        }
        if (event != null) {
            query.put("event", event);
        }
        if (status != null) {
            query.put("status", status.wireValue());
        }
        if (created != null) {
            query.put("created", created.toString());
        }
        if (excludePullRequests != null) {
            query.put("exclude_pull_requests", excludePullRequests.toString());
        }
        if (checkSuiteId != null) {
            query.put("check_suite_id", checkSuiteId.toString());
        }
        if (headSha != null) {
            query.put("head_sha", headSha);
        }

        return Collections.unmodifiableMap(query);
    }
}
