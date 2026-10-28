package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Organization Invitation.
 *
 * @param id Id
 * @param login Login
 * @param email Email
 * @param role Role
 * @param createdAt Created at
 * @param failedAt Failed at
 * @param failedReason Failed reason
 * @param inviter A GitHub user.
 * @param teamCount Team count
 * @param nodeId Node id
 * @param invitationTeamsUrl Invitation teams url
 * @param invitationSource Invitation source
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/organization-invitation")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationInvitation(
        long id,
        @Nullable String login,
        @Nullable String email,
        String role,
        String createdAt,
        @Nullable String failedAt,
        @Nullable String failedReason,
        GitHubSimpleUser inviter,
        long teamCount,
        String nodeId,
        String invitationTeamsUrl,
        @Nullable String invitationSource) {

    public GitHubOrganizationInvitation {
        requireNonNull(role, "role");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(inviter, "inviter");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(invitationTeamsUrl, "invitationTeamsUrl");
    }
}
