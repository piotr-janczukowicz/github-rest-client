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
 * Query filters for {@code GET /search/code}, excluding pagination ({@code page}, {@code per_page}).
 *
 * @param q search query
 * @param sort Deprecated: this field is closing down. Sorts the results of your query. Can only be {@code indexed}.
 * @param order Deprecated: this field is closing down. Ignored unless {@code sort} is provided.
 * @param textMatch whether to request text-match metadata via the Accept header
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/search/code/parameters")
@RecordBuilder
public record GitHubSearchCodeFilter(
        String q,
        @Deprecated @Nullable GitHubCodeSearchSort sort,
        @Deprecated @Nullable GitHubSearchOrder order,
        @JsonIgnore boolean textMatch) {

    public GitHubSearchCodeFilter {
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
