package io.github.piotr_janczukowicz.github.api.migrations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /user/migrations/{migration_id}}, excluding pagination.
 *
 * @param exclude Exclude attributes from the API response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/migrations/get-status-for-authenticated-user/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetStatusForAuthenticatedUserFilter(
        @Nullable List<String> exclude) {

    public GitHubGetStatusForAuthenticatedUserFilter {
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetStatusForAuthenticatedUserFilter empty() {
        return GitHubGetStatusForAuthenticatedUserFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter. Exclude values are comma-separated.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (exclude == null || exclude.isEmpty()) {
            return Map.of();
        }
        return Map.of("exclude", String.join(",", exclude));
    }
}
