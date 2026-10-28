package io.github.piotr_janczukowicz.github.api.dependencygraph;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/dependency-graph/compare/{basehead}}, excluding pagination.
 *
 * @param name The full path, relative to the repository root, of the dependency manifest file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/dependency-graph/diff-range/parameters")
@RecordBuilder
public record GitHubDiffRangeFilter(@Nullable String name) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubDiffRangeFilter empty() {
        return GitHubDiffRangeFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (name == null) {
            return Map.of();
        }

        return Map.of("name", name);
    }
}
