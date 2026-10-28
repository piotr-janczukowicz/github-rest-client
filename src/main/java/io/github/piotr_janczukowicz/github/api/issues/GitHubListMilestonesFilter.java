package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/milestones}, excluding pagination.
 *
 * @param state milestone state filter
 * @param sort sort field
 * @param direction sort direction
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-milestones/parameters")
@RecordBuilder
public record GitHubListMilestonesFilter(
        @Nullable GitHubListIssuesState state,
        @Nullable GitHubListMilestonesSort sort,
        @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListMilestonesFilter empty() {
        return GitHubListMilestonesFilterBuilder.builder().build();
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
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
