package io.github.piotr_janczukowicz.github.api.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/projectsV2} and {@code GET /users/{username}/projectsV2}, excluding
 * pagination.
 *
 * @param q Search query
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"operations/projects/list-for-org/parameters", "operations/projects/list-for-user/parameters"})
@RecordBuilder
public record GitHubListProjectsFilter(@Nullable String q) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListProjectsFilter empty() {
        return GitHubListProjectsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (q == null) {
            return Map.of();
        }
        return Map.of("q", q);
    }
}
