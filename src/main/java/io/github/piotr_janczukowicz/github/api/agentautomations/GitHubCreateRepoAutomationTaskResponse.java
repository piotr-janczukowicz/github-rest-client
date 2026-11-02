package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Response from accepting an automation run request.
 *
 * @param taskId The preallocated ID of the accepted run. The task may not yet be readable, and downstream skips or
 *     failures can prevent its creation.
 * @param automationId The ID of the automation that was triggered.
 * @param event The trigger event name, always "manual" for public API runs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/create-repo-automation-task/202")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepoAutomationTaskResponse(
        String taskId, String automationId, GitHubCreateRepoAutomationTaskResponseEvent event) {
    public GitHubCreateRepoAutomationTaskResponse {
        requireNonNull(taskId, "taskId");
        requireNonNull(automationId, "automationId");
        requireNonNull(event, "event");
    }
}
