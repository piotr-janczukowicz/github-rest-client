package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/visual-studio-subscriptions}, excluding pagination.
 *
 * @param isUnmatchedOnly Whether to return only unmatched subscriptions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-vss/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListVssFilter(@Nullable Boolean isUnmatchedOnly) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubListVssFilter empty() {
        return GitHubListVssFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (isUnmatchedOnly == null) {
            return Map.of();
        }

        return Map.of("is_unmatched_only", isUnmatchedOnly.toString());
    }
}
