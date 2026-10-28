package io.github.piotr_janczukowicz.github.api.licenses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /licenses}, excluding pagination.
 *
 * @param featured When set, filters featured licenses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/licenses/get-all-commonly-used/parameters")
@RecordBuilder
public record GitHubGetAllCommonlyUsedFilter(@Nullable Boolean featured) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetAllCommonlyUsedFilter empty() {
        return GitHubGetAllCommonlyUsedFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (featured == null) {
            return Map.of();
        }

        return Map.of("featured", featured.toString());
    }
}
