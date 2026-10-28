package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/contributors}, excluding pagination.
 *
 * @param anon include anonymous contributors when {@code true}; {@code false} sends no parameter, because GitHub may
 *     read any value of {@code anon}, {@code 0} included, as enabling it
 */
@CatalogSchema("operations/repos/list-contributors/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubListContributorsFilter(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @SuppressSchemaTypeMismatch("dump type is string; encoded JSON is the string true")
        @Nullable Boolean anon) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListContributorsFilter empty() {
        return GitHubListContributorsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (anon == null || !anon) {
            return Map.of();
        }
        return Map.of("anon", "1");
    }
}
