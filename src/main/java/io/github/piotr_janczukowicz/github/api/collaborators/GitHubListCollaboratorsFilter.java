package io.github.piotr_janczukowicz.github.api.collaborators;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/collaborators}, excluding pagination.
 *
 * @param affiliation Filter collaborators returned by their affiliation. {@code outside} means all outside
 *     collaborators of an organization-owned repository. {@code direct} means all collaborators with permissions to an
 *     organization-owned repository, regardless of organization membership status. {@code all} means all collaborators
 *     the authenticated user can see.
 * @param permission Filter collaborators by the permissions they have on the repository. If not specified, all
 *     collaborators will be returned.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-collaborators/parameters")
@RecordBuilder
public record GitHubListCollaboratorsFilter(
        @Nullable GitHubCollaboratorAffiliation affiliation,
        @Nullable GitHubCollaboratorPermission permission) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListCollaboratorsFilter empty() {
        return GitHubListCollaboratorsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (affiliation != null) {
            query.put("affiliation", affiliation.wireValue());
        }
        if (permission != null) {
            query.put("permission", permission.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
