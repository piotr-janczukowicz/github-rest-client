package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set a collaborator role for a Copilot Space.
 *
 * @param role The new role to grant to the collaborator. Use {@code no_access} to remove the collaborator.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/update-collaborator-for-org/requestBody",
    "operations/copilot-spaces/update-collaborator-for-user/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateCollaboratorPayload(GitHubUpdateCollaboratorRole role) {

    public GitHubUpdateCollaboratorPayload {
        requireNonNull(role, "role");
    }
}
