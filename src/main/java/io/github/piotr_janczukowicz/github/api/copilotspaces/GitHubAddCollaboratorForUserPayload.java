package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Add a collaborator to a Copilot Space for a user.
 *
 * @param actorType The type of actor (must be {@code User} for user-owned spaces; {@code Team} will be rejected).
 * @param actorIdentifier The username of the collaborator. The numeric user ID is also accepted.
 * @param role The role to grant to the collaborator.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot-spaces/add-collaborator-for-user/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddCollaboratorForUserPayload(
        GitHubCopilotSpaceActorType actorType, String actorIdentifier, GitHubCopilotSpaceCollaboratorRole role) {

    public GitHubAddCollaboratorForUserPayload {
        requireNonNull(actorType, "actorType");
        requireNonNull(actorIdentifier, "actorIdentifier");
        requireNonNull(role, "role");
    }
}
