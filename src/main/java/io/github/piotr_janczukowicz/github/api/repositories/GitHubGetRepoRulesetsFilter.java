package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.StringUtils.isNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/rulesets}, excluding pagination.
 *
 * @param includesParents Include rulesets configured at higher levels
 * @param targets Comma-separated ruleset targets
 */
@CatalogSchema("operations/repos/get-repo-rulesets/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubGetRepoRulesetsFilter(
        @Nullable Boolean includesParents, @Nullable String targets) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetRepoRulesetsFilter empty() {
        return GitHubGetRepoRulesetsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (includesParents != null) {
            query.put("includes_parents", includesParents.toString());
        }
        if (isNotBlank(targets)) {
            query.put("targets", targets);
        }

        return Collections.unmodifiableMap(query);
    }
}
