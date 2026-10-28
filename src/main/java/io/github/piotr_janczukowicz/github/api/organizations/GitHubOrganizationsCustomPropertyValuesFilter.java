package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/properties/values}, excluding pagination.
 *
 * @param repositoryQuery Repository search query
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/custom-properties-for-repos-get-organization-values/parameters")
@RecordBuilder
public record GitHubOrganizationsCustomPropertyValuesFilter(
        @Nullable String repositoryQuery) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsCustomPropertyValuesFilter empty() {
        return GitHubOrganizationsCustomPropertyValuesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (repositoryQuery == null) {
            return Map.of();
        }
        return Map.of("repository_query", repositoryQuery);
    }
}
