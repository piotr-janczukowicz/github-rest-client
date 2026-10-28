package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/code-scanning/alerts}, excluding pagination.
 *
 * @param toolName The name of a code scanning tool.
 * @param toolGuid The GUID of a code scanning tool.
 * @param ref The Git reference for the results you want to list.
 * @param pr The number of the pull request for the results you want to list.
 * @param direction The direction to sort the results by.
 * @param sort The property by which to sort the results.
 * @param state If specified, only code scanning alerts with this state will be returned.
 * @param severity If specified, only code scanning alerts with this severity will be returned.
 * @param assignees Filter alerts by assignees.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/list-alerts-for-repo/parameters")
public record GitHubListAlertsForRepoFilter(
        @Nullable String toolName,
        @Nullable String toolGuid,
        @Nullable String ref,
        @Nullable Integer pr,
        @Nullable GitHubDirection direction,
        @Nullable GitHubListAlertsForRepoSort sort,
        @Nullable GitHubCodeScanningAlertStateQuery state,
        @Nullable GitHubCodeScanningAlertSeverity severity,
        @Nullable String assignees) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAlertsForRepoFilter empty() {
        return GitHubListAlertsForRepoFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (toolName != null) {
            query.put("tool_name", toolName);
        }
        if (toolGuid != null) {
            query.put("tool_guid", toolGuid);
        }
        if (ref != null) {
            query.put("ref", ref);
        }
        if (pr != null) {
            query.put("pr", pr.toString());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (severity != null) {
            query.put("severity", severity.wireValue());
        }
        if (assignees != null) {
            query.put("assignees", assignees);
        }

        return Collections.unmodifiableMap(query);
    }
}
