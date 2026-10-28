package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reference data of an artifact. Jackson deduction selects the implementing record from the JSON object shape; a shape
 * that matches neither is kept raw as {@link GitHubListTasksTaskArtifactDataUnknown}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubListTasksTaskArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubListTasksTaskArtifactDataById.class),
    @JsonSubTypes.Type(GitHubListTasksTaskArtifactDataByRef.class)
})
@CatalogSchema(
        "operations/agent-tasks/list-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubListTasksTaskArtifactData
        permits GitHubListTasksTaskArtifactDataById,
                GitHubListTasksTaskArtifactDataByRef,
                GitHubListTasksTaskArtifactDataUnknown {}
