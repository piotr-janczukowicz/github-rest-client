package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/branches}, excluding pagination.
 *
 * @param protectedFilter Setting to {@code true} returns only branches protected by branch protections or rulesets.
 *     When set to {@code false}, only unprotected branches are returned. Omitting this parameter returns all branches.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-branches/parameters")
@RecordBuilder
public record GitHubListBranchesFilter(
        @JsonProperty("protected") @Nullable Boolean protectedFilter) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListBranchesFilter empty() {
        return GitHubListBranchesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (protectedFilter == null) {
            return Map.of();
        }

        return Map.of("protected", protectedFilter.toString());
    }
}
