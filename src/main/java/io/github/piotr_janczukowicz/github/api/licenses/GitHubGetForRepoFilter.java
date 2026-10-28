package io.github.piotr_janczukowicz.github.api.licenses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/license}, excluding pagination.
 *
 * @param ref The Git reference for the results you want to list. The {@code ref} for a branch can be formatted either
 *     as {@code refs/heads/<branch name>} or simply {@code <branch name>}. To reference a pull request use
 *     {@code refs/pull/<number>/merge}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/licenses/get-for-repo/parameters")
@RecordBuilder
public record GitHubGetForRepoFilter(@Nullable String ref) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetForRepoFilter empty() {
        return GitHubGetForRepoFilterBuilder.builder().build();
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
