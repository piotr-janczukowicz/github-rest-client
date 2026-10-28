package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /user/memberships/orgs}, excluding pagination.
 *
 * @param state Membership state filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/list-memberships-for-authenticated-user/parameters")
@RecordBuilder
public record GitHubOrganizationsAuthenticatedMembershipsFilter(
        @Nullable GitHubOrgMembershipState state) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsAuthenticatedMembershipsFilter empty() {
        return GitHubOrganizationsAuthenticatedMembershipsFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (state == null) {
            return Map.of();
        }
        return Map.of("state", state.wireValue());
    }
}
