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
 * Query filters for {@code GET /repos/{owner}/{repo}/code-scanning/analyses}, excluding pagination.
 *
 * @param toolName The name of a code scanning tool.
 * @param toolGuid The GUID of a code scanning tool.
 * @param pr The number of the pull request for the results you want to list.
 * @param ref The Git reference for the analyses you want to list.
 * @param sarifId Filter analyses belonging to the same SARIF upload.
 * @param direction The direction to sort the results by.
 * @param sort The property by which to sort the results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/list-recent-analyses/parameters")
public record GitHubListRecentAnalysesFilter(
        @Nullable String toolName,
        @Nullable String toolGuid,
        @Nullable Integer pr,
        @Nullable String ref,
        @Nullable String sarifId,
        @Nullable GitHubDirection direction,
        @Nullable GitHubListRecentAnalysesSort sort) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListRecentAnalysesFilter empty() {
        return GitHubListRecentAnalysesFilterBuilder.builder().build();
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
        if (pr != null) {
            query.put("pr", pr.toString());
        }
        if (ref != null) {
            query.put("ref", ref);
        }
        if (sarifId != null) {
            query.put("sarif_id", sarifId);
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
