package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Collaborators of a Copilot Space.
 *
 * @param collaborators The list of collaborators for this Copilot Space.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/list-collaborators-for-org/200",
    "operations/copilot-spaces/list-collaborators-for-user/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceCollaboratorsPage(List<GitHubCopilotSpaceCollaborator> collaborators) {

    public GitHubCopilotSpaceCollaboratorsPage {
        requireNonNull(collaborators, "collaborators");
    }
}
