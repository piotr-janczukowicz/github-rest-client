package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Resource data (shape depends on type). */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubListRepoAutomationTasksTaskArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubListRepoAutomationTasksTaskArtifactDataById.class),
    @JsonSubTypes.Type(GitHubListRepoAutomationTasksTaskArtifactDataByRef.class)
})
@CatalogSchema(
        "operations/agent-automations/list-repo-automation-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubListRepoAutomationTasksTaskArtifactData
        permits GitHubListRepoAutomationTasksTaskArtifactDataById,
                GitHubListRepoAutomationTasksTaskArtifactDataByRef,
                GitHubListRepoAutomationTasksTaskArtifactDataUnknown {}
