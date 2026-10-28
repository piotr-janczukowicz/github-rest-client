package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /gists}, excluding pagination.
 *
 * @param since Only show results that were last updated after the given time.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/list/parameters")
@RecordBuilder
public record GitHubListFilter(@Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListFilter empty() {
        return GitHubListFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (since == null) {
            return Map.of();
        }

        return Map.of("since", since.toString());
    }
}
