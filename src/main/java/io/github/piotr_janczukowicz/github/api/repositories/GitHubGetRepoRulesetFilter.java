package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}}, excluding pagination.
 *
 * @param includesParents Include rulesets configured at higher levels
 */
@CatalogSchema("operations/repos/get-repo-ruleset/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubGetRepoRulesetFilter(@Nullable Boolean includesParents) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetRepoRulesetFilter empty() {
        return new GitHubGetRepoRulesetFilter(null);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (includesParents == null) {
            return Map.of();
        }

        return Map.of("includes_parents", includesParents.toString());
    }
}
