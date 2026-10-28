package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;

/**
 * Query filters for {@code GET /search/topics}, excluding pagination ({@code page}, {@code per_page}).
 *
 * @param q search query
 * @param textMatch whether to request text-match metadata via the Accept header
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/search/topics/parameters")
@RecordBuilder
public record GitHubSearchTopicsFilter(String q, @JsonIgnore boolean textMatch) {

    public GitHubSearchTopicsFilter {
        requireNotBlank(q, "q");
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        return Map.of("q", q);
    }
}
