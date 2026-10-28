package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reference data of an artifact. Jackson deduction selects the implementing record from the JSON object shape; a shape
 * that matches neither is kept raw as {@link GitHubGetTaskByRepoAndIdResponseArtifactDataUnknown}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubGetTaskByRepoAndIdResponseArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubGetTaskByRepoAndIdResponseArtifactDataById.class),
    @JsonSubTypes.Type(GitHubGetTaskByRepoAndIdResponseArtifactDataByRef.class)
})
@CatalogSchema("operations/agent-tasks/get-task-by-repo-and-id/200/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubGetTaskByRepoAndIdResponseArtifactData
        permits GitHubGetTaskByRepoAndIdResponseArtifactDataById,
                GitHubGetTaskByRepoAndIdResponseArtifactDataByRef,
                GitHubGetTaskByRepoAndIdResponseArtifactDataUnknown {}
