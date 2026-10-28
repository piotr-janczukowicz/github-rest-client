package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /user/repos}, excluding pagination.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.issues.GitHubListForAuthenticatedUserFilter}.
 *
 * @param visibility visibility filter
 * @param affiliation comma-separated list of values: {@code owner} (repositories owned by the authenticated user),
 *     {@code collaborator} (repositories the user was added to as a collaborator) and {@code organization_member}
 *     (repositories reached through membership of an organization, including every repository of the user's teams).
 *     GitHub's default is all three, {@code owner,collaborator,organization_member}; it cannot be combined with
 *     {@code type}
 * @param type repository type filter
 * @param sort sort field
 * @param direction sort direction
 * @param since only show repositories updated after this time
 * @param before only show repositories updated before this time
 */
@CatalogSchema("operations/repos/list-for-authenticated-user/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubListForAuthenticatedUserFilter(
        @Nullable GitHubAuthenticatedUserReposVisibility visibility,
        @Nullable String affiliation,
        @Nullable GitHubAuthenticatedUserReposType type,
        @Nullable GitHubReposSort sort,
        @Nullable GitHubReposSortDirection direction,
        @Nullable Instant since,
        @Nullable Instant before) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForAuthenticatedUserFilter empty() {
        return GitHubListForAuthenticatedUserFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (visibility != null) {
            query.put("visibility", visibility.wireValue());
        }
        if (affiliation != null) {
            query.put("affiliation", affiliation);
        }
        if (type != null) {
            query.put("type", type.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (since != null) {
            query.put("since", since.toString());
        }
        if (before != null) {
            query.put("before", before.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
