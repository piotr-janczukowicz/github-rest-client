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
 * Query filters for {@code GET /search/users}, excluding pagination ({@code page}, {@code per_page}).
 *
 * @param q search query
 * @param sort sort field
 * @param order sort order
 * @param textMatch whether to request text-match metadata via the Accept header
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/search/users/parameters")
@RecordBuilder
public record GitHubSearchUsersFilter(
        String q,
        @Nullable GitHubUserSearchSort sort,
        @Nullable GitHubSearchOrder order,
        @JsonIgnore boolean textMatch) {

    public GitHubSearchUsersFilter {
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
        return Collections.unmodifiableMap(query);
    }
}
