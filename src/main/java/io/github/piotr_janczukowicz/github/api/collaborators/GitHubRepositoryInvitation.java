package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Repository invitations let you manage who you collaborate with.
 *
 * @param id Unique identifier of the repository invitation.
 * @param repository Repository
 * @param invitee Invitee
 * @param inviter Inviter
 * @param permissions The permission associated with the invitation.
 * @param createdAt Created at
 * @param expired Whether or not the invitation has expired
 * @param url URL for the repository invitation
 * @param htmlUrl Html url
 * @param nodeId Node id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository-invitation")
@RecordBuilder
public record GitHubRepositoryInvitation(
        long id,
        GitHubMinimalRepository repository,
        @Nullable GitHubSimpleUser invitee,
        @Nullable GitHubSimpleUser inviter,
        GitHubRepositoryInvitationPermission permissions,
        Instant createdAt,
        @Nullable Boolean expired,
        String url,
        String htmlUrl,
        String nodeId) {

    public GitHubRepositoryInvitation {
        requireNonNull(repository, "repository");
        requireNonNull(permissions, "permissions");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(nodeId, "nodeId");
    }
}
