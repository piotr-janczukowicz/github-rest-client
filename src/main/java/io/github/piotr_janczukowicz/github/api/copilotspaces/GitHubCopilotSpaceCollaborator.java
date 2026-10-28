package io.github.piotr_janczukowicz.github.api.copilotspaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** A collaborator (user or team) of a Copilot Space. */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "actor_type",
        visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GitHubCopilotSpaceUserCollaborator.class, name = "User"),
    @JsonSubTypes.Type(value = GitHubCopilotSpaceTeamCollaborator.class, name = "Team")
})
@CatalogSchema("components/schemas/copilot-space-collaborator")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubCopilotSpaceCollaborator
        permits GitHubCopilotSpaceUserCollaborator, GitHubCopilotSpaceTeamCollaborator {}
