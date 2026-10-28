package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reference data of an artifact. Jackson deduction selects the implementing record from the JSON object shape; a shape
 * that matches neither is kept raw as {@link GitHubGetTaskByIdResponseArtifactDataUnknown}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubGetTaskByIdResponseArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubGetTaskByIdResponseArtifactDataById.class),
    @JsonSubTypes.Type(GitHubGetTaskByIdResponseArtifactDataByRef.class)
})
@CatalogSchema("operations/agent-tasks/get-task-by-id/200/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubGetTaskByIdResponseArtifactData
        permits GitHubGetTaskByIdResponseArtifactDataById,
                GitHubGetTaskByIdResponseArtifactDataByRef,
                GitHubGetTaskByIdResponseArtifactDataUnknown {}
