package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create organization invitation request body.
 *
 * <p>Provide {@code inviteeId} or {@code email} per GitHub API docs.
 *
 * @param inviteeId GitHub user ID for the person you are inviting
 * @param email Email address of the person you are inviting
 * @param role Role for the new member
 * @param teamIds Team IDs to invite the new member to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/create-invitation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateOrganizationInvitationPayload(
        @Nullable Long inviteeId,
        @Nullable String email,
        @Nullable GitHubOrganizationInvitationRole role,
        @Nullable List<Long> teamIds) {

    public GitHubCreateOrganizationInvitationPayload {
        if (teamIds != null) {
            teamIds = List.copyOf(teamIds);
        }
    }
}
