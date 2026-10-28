package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Add a collaborator to an organization Copilot Space.
 *
 * @param actorType The type of actor (user or team).
 * @param actorIdentifier The username (for users) or team slug (for teams). The numeric ID of a user or team is also
 *     accepted.
 * @param role The role to grant to the collaborator.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot-spaces/add-collaborator-for-org/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddCollaboratorForOrgPayload(
        GitHubCopilotSpaceActorType actorType, String actorIdentifier, GitHubCopilotSpaceCollaboratorRole role) {

    public GitHubAddCollaboratorForOrgPayload {
        requireNonNull(actorType, "actorType");
        requireNonNull(actorIdentifier, "actorIdentifier");
        requireNonNull(role, "role");
    }
}
