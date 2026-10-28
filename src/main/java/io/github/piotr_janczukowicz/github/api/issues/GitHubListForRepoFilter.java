package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/issues}, excluding pagination.
 *
 * @param milestone milestone number, {@code *}, or {@code none}
 * @param state issue state filter
 * @param assignee assignee username, {@code *}, or {@code none}
 * @param type issue type name, {@code *}, or {@code none}
 * @param creator username of the issue creator
 * @param mentioned username mentioned in the issue
 * @param issueFieldValues comma-separated issue field filters in {@code field_slug:value} format
 * @param labels comma-separated list of label names
 * @param sort sort field
 * @param direction sort direction
 * @param since only issues updated at or after this time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-for-repo/parameters")
@RecordBuilder
public record GitHubListForRepoFilter(
        @Nullable String milestone,
        @Nullable GitHubListIssuesState state,
        @Nullable String assignee,
        @Nullable String type,
        @Nullable String creator,
        @Nullable String mentioned,
        @Nullable String issueFieldValues,
        @Nullable String labels,
        @Nullable GitHubListIssuesSort sort,
        @Nullable GitHubDirection direction,
        @Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForRepoFilter empty() {
        return GitHubListForRepoFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (milestone != null) {
            query.put("milestone", milestone);
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (assignee != null) {
            query.put("assignee", assignee);
        }
        if (type != null) {
            query.put("type", type);
        }
        if (creator != null) {
            query.put("creator", creator);
        }
        if (mentioned != null) {
            query.put("mentioned", mentioned);
        }
        if (issueFieldValues != null) {
            query.put("issue_field_values", issueFieldValues);
        }
        if (labels != null) {
            query.put("labels", labels);
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (since != null) {
            query.put("since", since.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
