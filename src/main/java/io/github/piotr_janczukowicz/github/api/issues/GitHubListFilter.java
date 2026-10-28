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
 * Query filters for {@code GET /issues}, excluding pagination.
 *
 * @param filter which sorts of issues to return
 * @param state issue state filter
 * @param labels comma-separated list of label names
 * @param sort sort field
 * @param direction sort direction
 * @param since only issues updated at or after this time
 * @param collab include collaborator-owned repositories
 * @param orgs include organization-owned repositories
 * @param owned include owned repositories
 * @param pulls include pull requests
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list/parameters")
@RecordBuilder
public record GitHubListFilter(
        @Nullable GitHubListIssuesFilterKind filter,
        @Nullable GitHubListIssuesState state,
        @Nullable String labels,
        @Nullable GitHubListIssuesSort sort,
        @Nullable GitHubDirection direction,
        @Nullable Instant since,
        @Nullable Boolean collab,
        @Nullable Boolean orgs,
        @Nullable Boolean owned,
        @Nullable Boolean pulls) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListFilter empty() {
        return GitHubListFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (filter != null) {
            query.put("filter", filter.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
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
        if (collab != null) {
            query.put("collab", collab.toString());
        }
        if (orgs != null) {
            query.put("orgs", orgs.toString());
        }
        if (owned != null) {
            query.put("owned", owned.toString());
        }
        if (pulls != null) {
            query.put("pulls", pulls.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
