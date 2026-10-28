package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for editing an automation.
 *
 * @param name The name of the automation.
 * @param enabled Set to true to enable, false to disable. Omit to leave unchanged.
 * @param writeScope Controls which repository resources a run may modify. {@code trigger} limits writes to the resource
 *     that started the run, when one exists, and resources created during the run; {@code repository} allows writes to
 *     any resource in the repository. Supported values: {@code trigger}, {@code repository}. Omit to leave unchanged.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. Does not apply to
 *     interval-scheduled runs, which always run as the automation owner. Omit to leave unchanged.
 * @param model The model to use for sessions triggered by this automation. Omit to leave unchanged. Send an empty
 *     string to clear the model and revert to the platform default.
 * @param reasoningEffort The reasoning effort to use for sessions triggered by this automation. Omit to leave
 *     unchanged. Send an empty string to clear the override. Currently supported values: {@code none}, {@code low},
 *     {@code medium}, {@code high}, {@code xhigh}, {@code max}
 * @param triggers Public trigger configuration keyed by stable public trigger identifier. Each trigger has a closed,
 *     trigger-specific configuration schema.
 * @param prompt The prompt template for the automation.
 * @param tools The tools available to the automation. Send an empty array to clear all tools.
 * @param allowedCustomAgents Custom agents (subagents) this automation is allowed to invoke. Send an empty array to
 *     clear all custom agents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/edit-repo-automation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationPayload(
        @Nullable String name,
        @Nullable Boolean enabled,
        @Nullable GitHubEditRepoAutomationWriteScope writeScope,
        @Nullable Boolean requireActorWritePermission,
        @Nullable String model,
        @Nullable GitHubEditRepoAutomationReasoningEffort reasoningEffort,
        @Nullable GitHubEditRepoAutomationTriggers triggers,
        @Nullable String prompt,
        @Nullable List<String> tools,
        @Nullable List<String> allowedCustomAgents) {
    public GitHubEditRepoAutomationPayload {
        if (tools != null) {
            tools = List.copyOf(tools);
        }
        if (allowedCustomAgents != null) {
            allowedCustomAgents = List.copyOf(allowedCustomAgents);
        }
    }
}
