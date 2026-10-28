package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/rulesets/rule-suites/{rule_suite_id}}, excluding pagination.
 *
 * @param includeMetadata When {@code true}, the response includes metadata for pull request and required status check
 *     rule evaluations. Results are capped at 50 entries.
 */
@CatalogSchema("operations/repos/get-repo-rule-suite/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubGetRepoRuleSuiteFilter(@Nullable Boolean includeMetadata) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetRepoRuleSuiteFilter empty() {
        return GitHubGetRepoRuleSuiteFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (includeMetadata == null) {
            return Map.of();
        }

        return Map.of("include_metadata", includeMetadata.toString());
    }
}
