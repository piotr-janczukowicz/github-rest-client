package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reference data of an artifact. Jackson deduction selects the implementing record from the JSON object shape; a shape
 * that matches neither is kept raw as {@link GitHubCreateTaskInRepoResponseArtifactDataUnknown}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubCreateTaskInRepoResponseArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubCreateTaskInRepoResponseArtifactDataById.class),
    @JsonSubTypes.Type(GitHubCreateTaskInRepoResponseArtifactDataByRef.class)
})
@CatalogSchema("operations/agent-tasks/create-task-in-repo/201/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubCreateTaskInRepoResponseArtifactData
        permits GitHubCreateTaskInRepoResponseArtifactDataById,
                GitHubCreateTaskInRepoResponseArtifactDataByRef,
                GitHubCreateTaskInRepoResponseArtifactDataUnknown {}
