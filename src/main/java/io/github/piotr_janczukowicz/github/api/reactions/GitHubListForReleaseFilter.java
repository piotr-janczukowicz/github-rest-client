package io.github.piotr_janczukowicz.github.api.reactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/releases/{release_id}/reactions}, excluding pagination.
 *
 * @param content Returns a single reaction type. Omit this parameter to list all reactions to a release.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/reactions/list-for-release/parameters")
@RecordBuilder
public record GitHubListForReleaseFilter(@Nullable GitHubReleaseReactionContent content) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForReleaseFilter empty() {
        return GitHubListForReleaseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (content == null) {
            return Map.of();
        }

        return Map.of("content", content.wireValue());
    }
}
