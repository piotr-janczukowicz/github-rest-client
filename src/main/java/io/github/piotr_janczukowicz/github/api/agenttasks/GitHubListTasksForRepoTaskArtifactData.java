package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reference data of an artifact. Jackson deduction selects the implementing record from the JSON object shape; a shape
 * that matches neither is kept raw as {@link GitHubListTasksForRepoTaskArtifactDataUnknown}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubListTasksForRepoTaskArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubListTasksForRepoTaskArtifactDataById.class),
    @JsonSubTypes.Type(GitHubListTasksForRepoTaskArtifactDataByRef.class)
})
@CatalogSchema(
        "operations/agent-tasks/list-tasks-for-repo/200/properties/tasks/items/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubListTasksForRepoTaskArtifactData
        permits GitHubListTasksForRepoTaskArtifactDataById,
                GitHubListTasksForRepoTaskArtifactDataByRef,
                GitHubListTasksForRepoTaskArtifactDataUnknown {}
