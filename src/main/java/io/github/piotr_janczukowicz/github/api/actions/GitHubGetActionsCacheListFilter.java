package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/caches}, excluding pagination.
 *
 * @param ref The Git reference for the cache
 * @param key An explicit key or prefix for identifying the cache
 * @param sort The property by which to sort the results
 * @param direction The direction to sort the results by
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/get-actions-cache-list/parameters")
@RecordBuilder
public record GitHubGetActionsCacheListFilter(
        @Nullable String ref,
        @Nullable String key,
        @Nullable GitHubActionsCacheListSort sort,
        @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetActionsCacheListFilter empty() {
        return GitHubGetActionsCacheListFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (ref != null) {
            query.put("ref", ref);
        }
        if (key != null) {
            query.put("key", key);
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
