package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Artifact produced by the task.
 *
 * @param provider Provider
 * @param type Type
 * @param data Data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/get-task-by-repo-and-id/200/properties/artifacts/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetTaskByRepoAndIdResponseArtifact(
        GitHubGetTaskByRepoAndIdResponseArtifactProvider provider,
        GitHubGetTaskByRepoAndIdResponseArtifactType type,
        GitHubGetTaskByRepoAndIdResponseArtifactData data) {
    public GitHubGetTaskByRepoAndIdResponseArtifact {
        requireNonNull(provider, "provider");
        requireNonNull(type, "type");
        requireNonNull(data, "data");
    }
}
