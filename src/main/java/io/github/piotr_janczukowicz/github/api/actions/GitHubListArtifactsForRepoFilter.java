package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/artifacts}, excluding pagination.
 *
 * @param name The name field of an artifact
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/list-artifacts-for-repo/parameters")
@RecordBuilder
public record GitHubListArtifactsForRepoFilter(@Nullable String name) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListArtifactsForRepoFilter empty() {
        return GitHubListArtifactsForRepoFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (name == null) {
            return Map.of();
        }

        return Map.of("name", name);
    }
}
