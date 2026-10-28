package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Resource data (shape depends on type). */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubListAutomationTasksTaskArtifactDataUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubListAutomationTasksTaskArtifactDataById.class),
    @JsonSubTypes.Type(GitHubListAutomationTasksTaskArtifactDataByRef.class)
})
@CatalogSchema(
        "operations/agent-automations/list-automation-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubListAutomationTasksTaskArtifactData
        permits GitHubListAutomationTasksTaskArtifactDataById,
                GitHubListAutomationTasksTaskArtifactDataByRef,
                GitHubListAutomationTasksTaskArtifactDataUnknown {}
