package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /search/issues}, excluding pagination ({@code page}, {@code per_page}).
 *
 * @param q search query
 * @param sort sort field
 * @param order sort order
 * @param advancedSearch advanced search query value
 * @param searchType issue search type ({@code semantic} or {@code hybrid})
 * @param textMatch whether to request text-match metadata via the Accept header
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/search/issues-and-pull-requests/parameters")
@RecordBuilder
public record GitHubSearchIssuesFilter(
        String q,
        @Nullable GitHubIssueSearchSort sort,
        @Nullable GitHubSearchOrder order,
        @Nullable String advancedSearch,
        @Nullable GitHubIssueSearchQueryType searchType,
        @JsonIgnore boolean textMatch) {

    public GitHubSearchIssuesFilter {
        requireNotBlank(q, "q");
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        query.put("q", q);
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (order != null) {
            query.put("order", order.wireValue());
        }
        if (advancedSearch != null) {
            query.put("advanced_search", advancedSearch);
        }
        if (searchType != null) {
            query.put("search_type", searchType.wireValue());
        }
        return Collections.unmodifiableMap(query);
    }
}
