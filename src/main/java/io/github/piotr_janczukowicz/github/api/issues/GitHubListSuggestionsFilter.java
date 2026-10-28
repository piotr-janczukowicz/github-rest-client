package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/issues/{issue_number}/suggestions}, excluding pagination.
 *
 * @param state suggestion state filter
 * @param action suggestion action filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/list-suggestions/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListSuggestionsFilter(
        @Nullable GitHubListIssueSuggestionsState state,
        @Nullable GitHubIssueSuggestionAction action) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSuggestionsFilter empty() {
        return GitHubListSuggestionsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (action != null) {
            query.put("action", action.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
