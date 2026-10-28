package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /user/starred}, excluding pagination.
 *
 * @param sort The field to sort the results by.
 * @param direction The direction to sort the results by.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/list-repos-starred-by-authenticated-user/parameters")
@RecordBuilder
public record GitHubListReposStarredByAuthenticatedUserFilter(
        @Nullable GitHubSortStarred sort, @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListReposStarredByAuthenticatedUserFilter empty() {
        return GitHubListReposStarredByAuthenticatedUserFilterBuilder.builder().build();
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

        return Collections.unmodifiableMap(query);
    }
}
