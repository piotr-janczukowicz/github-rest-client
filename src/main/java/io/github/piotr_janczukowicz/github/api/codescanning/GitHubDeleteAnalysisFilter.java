package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code DELETE /repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}}, excluding pagination.
 *
 * @param confirmDelete Allow deletion if the specified analysis is the last in a set.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/delete-analysis/parameters")
public record GitHubDeleteAnalysisFilter(@Nullable String confirmDelete) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubDeleteAnalysisFilter empty() {
        return GitHubDeleteAnalysisFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (confirmDelete == null) {
            return Map.of();
        }

        return Map.of("confirm_delete", confirmDelete);
    }
}
