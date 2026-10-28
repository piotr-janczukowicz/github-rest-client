package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repositories}, excluding pagination.
 *
 * @param visibility the types of repositories to return, {@code public} by default. Only the {@code GHES} flavor
 *     defines this parameter: the {@code CLOUD} and {@code GHEC} descriptions of {@code GET /repositories} have none,
 *     so set it for a GitHub Enterprise Server client only. The instance lists only repositories available to all of
 *     its users
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-public/parameters")
@RecordBuilder
public record GitHubListPublicFilter(@Nullable GitHubPublicReposVisibility visibility) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPublicFilter empty() {
        return GitHubListPublicFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (visibility == null) {
            return Map.of();
        }

        return Map.of("visibility", visibility.wireValue());
    }
}
