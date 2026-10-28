package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/outside_collaborators}, excluding pagination.
 *
 * @param filter Outside collaborators filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/list-outside-collaborators/parameters")
@RecordBuilder
public record GitHubOrganizationsOutsideCollaboratorsFilter(
        @Nullable GitHubOrganizationOutsideCollaboratorsKind filter) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsOutsideCollaboratorsFilter empty() {
        return GitHubOrganizationsOutsideCollaboratorsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (filter == null) {
            return Map.of();
        }

        return Map.of("filter", filter.wireValue());
    }
}
