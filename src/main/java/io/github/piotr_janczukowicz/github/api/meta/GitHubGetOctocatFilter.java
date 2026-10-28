package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /octocat}, excluding pagination.
 *
 * @param s The words to show in Octocat's speech bubble
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/meta/get-octocat/parameters")
@RecordBuilder
public record GitHubGetOctocatFilter(@Nullable String s) {

    /**
     * Filter that sets no query parameter.
     *
     * @return filter whose {@link #toQueryParameters()} is empty
     */
    public static GitHubGetOctocatFilter empty() {
        return GitHubGetOctocatFilterBuilder.builder().build();
    }

    /**
     * Query parameters for the request.
     *
     * @return {@code s} when set, otherwise an empty map; unmodifiable
     */
    public Map<String, String> toQueryParameters() {
        if (s == null) {
            return Map.of();
        }

        return Map.of("s", s);
    }
}
