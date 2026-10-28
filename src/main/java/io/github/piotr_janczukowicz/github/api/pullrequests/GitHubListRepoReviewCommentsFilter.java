package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/pulls/comments}, excluding pagination.
 *
 * @param sort Sort field
 * @param direction Sort direction
 * @param since Only show comments updated after this time
 */
@CatalogSchema("operations/pulls/list-review-comments-for-repo/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubListRepoReviewCommentsFilter(
        @Nullable GitHubListRepoReviewCommentsSort sort,
        @Nullable GitHubListPullRequestsDirection direction,
        @Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListRepoReviewCommentsFilter empty() {
        return GitHubListRepoReviewCommentsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

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
