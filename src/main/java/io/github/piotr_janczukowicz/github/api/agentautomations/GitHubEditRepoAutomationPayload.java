package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for editing an automation. Omitted fields are left unchanged. Explicit null values are rejected.
 *
 * @param name The name of the automation.
 * @param enabled Set to true to enable, false to disable. Omit to leave unchanged.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. When policy validation is enabled,
 *     explicitly setting false returns 422 if effective organization or repository policy disallows read-access
 *     triggers. The actor requirement does not apply to interval-scheduled runs, which always run as the automation
 *     owner. Omit to leave unchanged.
 * @param continuousHistory When true, runs of this automation build on the session history of its previous runs, and
 *     runs of the same automation are serialized. Not tracked in revision history. Omit to leave unchanged.
 * @param model The model to use for sessions triggered by this automation. Omit to leave unchanged. Send an empty
 *     string to clear the override and let the platform select the model.
 * @param reasoningEffort The reasoning effort to use for sessions triggered by this automation. Omit to leave
 *     unchanged. Send an empty string to clear the override and use the model's default.
 * @param triggers Trigger configuration for the automation.
 * @param prompt The prompt for the automation.
 * @param tools The tools available to the automation. Send an empty array to clear all tools.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/edit-repo-automation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationPayload(
        @Nullable String name,
        @Nullable Boolean enabled,
        @Nullable Boolean requireActorWritePermission,
        @Nullable Boolean continuousHistory,
        @Nullable String model,
        @Nullable GitHubEditRepoAutomationReasoningEffort reasoningEffort,
        @Nullable GitHubEditRepoAutomationTriggers triggers,
        @Nullable String prompt,
        @Nullable List<String> tools) {
    public GitHubEditRepoAutomationPayload {
        requireMaxLengthIfPresent(prompt, 10_000, "prompt");
        if (tools != null) {
            tools = List.copyOf(tools);
        }
    }
}
