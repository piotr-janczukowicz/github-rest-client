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
 * Query filters for {@code GET /orgs/{org}/issues}, excluding pagination.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.repositories.GitHubListForOrgFilter} and
 * {@link io.github.piotr_janczukowicz.github.api.migrations.GitHubListForOrgFilter}.
 *
 * @param filter which sorts of issues to return
 * @param state issue state filter
 * @param labels comma-separated list of label names
 * @param type issue type name filter
 * @param sort sort field
 * @param direction sort direction
 * @param since only issues updated at or after this time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-for-org/parameters")
@RecordBuilder
public record GitHubListForOrgFilter(
        @Nullable GitHubListIssuesFilterKind filter,
        @Nullable GitHubListIssuesState state,
        @Nullable String labels,
        @Nullable String type,
        @Nullable GitHubListIssuesSort sort,
        @Nullable GitHubDirection direction,
        @Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForOrgFilter empty() {
        return GitHubListForOrgFilterBuilder.builder().build();
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
        if (type != null) {
            query.put("type", type);
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
