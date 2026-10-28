package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.StringUtils.isBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/codeowners/errors}, excluding pagination.
 *
 * @param ref branch, tag, or commit name
 */
@CatalogSchema("operations/repos/codeowners-errors/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubCodeownersErrorsFilter(@Nullable String ref) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubCodeownersErrorsFilter empty() {
        return new GitHubCodeownersErrorsFilter(null);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (isBlank(ref)) {
            return Map.of();
        }

        return Map.of("ref", ref);
    }
}
