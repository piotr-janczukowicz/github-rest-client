package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/invitations}, excluding pagination.
 *
 * @param role Role filter
 * @param invitationSource Invitation source filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/list-pending-invitations/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationsPendingInvitationsFilter(
        @Nullable GitHubOrganizationPendingInvitationRole role,
        @Nullable GitHubOrganizationPendingInvitationSource invitationSource) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsPendingInvitationsFilter empty() {
        return GitHubOrganizationsPendingInvitationsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (role != null) {
            query.put("role", role.wireValue());
        }
        if (invitationSource != null) {
            query.put("invitation_source", invitationSource.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
