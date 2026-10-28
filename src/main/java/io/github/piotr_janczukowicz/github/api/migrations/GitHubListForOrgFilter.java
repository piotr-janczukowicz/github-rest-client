package io.github.piotr_janczukowicz.github.api.migrations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/migrations} and {@code GET /orgs/{org}/migrations/{migration_id}}, excluding
 * pagination.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.issues.GitHubListForOrgFilter} and
 * {@link io.github.piotr_janczukowicz.github.api.repositories.GitHubListForOrgFilter}.
 *
 * @param exclude Exclude attributes from the API response to improve performance
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"operations/migrations/list-for-org/parameters", "operations/migrations/get-status-for-org/parameters"})
@RecordBuilder
public record GitHubListForOrgFilter(@Nullable List<GitHubMigrationExclude> exclude) {

    public GitHubListForOrgFilter {
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForOrgFilter empty() {
        return GitHubListForOrgFilterBuilder.builder().build();
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
        return Map.of(
                "exclude",
                String.join(
                        ",",
                        exclude.stream().map(GitHubMigrationExclude::wireValue).toList()));
    }
}
