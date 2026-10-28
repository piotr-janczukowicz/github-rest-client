package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/pulls}, excluding pagination.
 *
 * @param state Pull request state filter
 * @param head Head user or organization and branch name filter
 * @param base Base branch name filter
 * @param sort Sort field
 * @param direction Sort direction
 */
@CatalogSchema("operations/pulls/list/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubListPullRequestsFilter(
        @Nullable GitHubListPullRequestsState state,
        @Nullable String head,
        @Nullable String base,
        @Nullable GitHubListPullRequestsSort sort,
        @Nullable GitHubListPullRequestsDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPullRequestsFilter empty() {
        return GitHubListPullRequestsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (head != null) {
            query.put("head", head);
        }
        if (base != null) {
            query.put("base", base);
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
