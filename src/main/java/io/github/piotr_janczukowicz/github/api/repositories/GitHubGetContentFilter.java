package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/contents/{path}}, excluding pagination.
 *
 * @param ref The name of the commit/branch/tag. Default: the repository’s default branch.
 */
@CatalogSchema({
    "operations/repos/get-content/parameters",
    "operations/repos/get-readme/parameters",
    "operations/repos/get-readme-in-directory/parameters"
})
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubGetContentFilter(@Nullable String ref) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetContentFilter empty() {
        return GitHubGetContentFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (ref == null) {
            return Map.of();
        }

        return Map.of("ref", ref);
    }
}
