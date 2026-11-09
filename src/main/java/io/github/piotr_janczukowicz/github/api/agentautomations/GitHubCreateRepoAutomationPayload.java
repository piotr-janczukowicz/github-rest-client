package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for creating an automation.
 *
 * @param name The name of the automation.
 * @param triggers Trigger configuration for the automation.
 * @param prompt The prompt for the automation.
 * @param model The model to use for sessions triggered by this automation. Omit or leave empty to let the platform
 *     select the model.
 * @param reasoningEffort The reasoning effort to use for sessions triggered by this automation. Omit or leave empty to
 *     use the model's default.
 * @param tools The tools available to the automation.
 * @param enabled Whether the automation should be created enabled. Defaults to true when omitted.
 * @param type The type of automation, fixed at creation. {@code personal} keeps it private to its creator and its runs
 *     act as that user. {@code shared} makes it belong to the repository, visible to anyone with read access to the
 *     repository and maintainable by anyone with write access to the repository, and its runs act as the Copilot app
 *     rather than as a person. {@code shared} is rejected when repository ownership is not available for the
 *     repository. Supported values: {@code personal}, {@code shared}. Defaults to {@code personal}.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. When policy validation is enabled,
 *     explicitly setting false returns 422 if effective organization or repository policy disallows read-access
 *     triggers. If omitted, defaults to false when policy permits read-access triggers, otherwise true. The actor
 *     requirement does not apply to interval-scheduled runs, which always run as the automation owner.
 * @param continuousHistory When true, runs of this automation build on the session history of its previous runs, and
 *     runs of the same automation are serialized. Not tracked in revision history. Defaults to false.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/create-repo-automation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepoAutomationPayload(
        String name,
        @Nullable GitHubAutomationTriggerPayload triggers,
        String prompt,
        @Nullable String model,
        @Nullable GitHubAutomationReasoningEffort reasoningEffort,
        @Nullable List<String> tools,
        @Nullable Boolean enabled,
        @Nullable GitHubCreateRepoAutomationType type,
        @Nullable Boolean requireActorWritePermission,
        @Nullable Boolean continuousHistory) {
    public GitHubCreateRepoAutomationPayload {
        requireNonNull(name, "name");
        requireNonNull(prompt, "prompt");
        requireMaxLength(prompt, 10_000, "prompt");
        if (tools != null) {
            tools = List.copyOf(tools);
        }
    }
}
