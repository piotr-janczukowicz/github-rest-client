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
 * Query filters for {@code GET /repos/{owner}/{repo}/issues/comments}, excluding pagination.
 *
 * @param sort sort field
 * @param direction sort direction
 * @param since only comments updated at or after this time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-comments-for-repo/parameters")
@RecordBuilder
public record GitHubListCommentsForRepoFilter(
        @Nullable GitHubListCommentsForRepoSort sort,
        @Nullable GitHubDirection direction,
        @Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListCommentsForRepoFilter empty() {
        return GitHubListCommentsForRepoFilterBuilder.builder().build();
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
