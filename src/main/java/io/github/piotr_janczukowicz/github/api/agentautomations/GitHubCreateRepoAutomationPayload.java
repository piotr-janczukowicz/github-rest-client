package io.github.piotr_janczukowicz.github.api.agentautomations;

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
 * @param triggers Public trigger configuration keyed by stable public trigger identifier. Each trigger has a closed,
 *     trigger-specific configuration schema.
 * @param prompt The prompt template for the automation.
 * @param model The model to use for sessions triggered by this automation. Omit to use the platform default.
 * @param reasoningEffort The reasoning effort to use for sessions triggered by this automation. Omit or leave empty for
 *     no override. Currently supported values: {@code none}, {@code low}, {@code medium}, {@code high}, {@code xhigh},
 *     {@code max}
 * @param tools The tools available to the automation.
 * @param allowedCustomAgents Custom agents (subagents) this automation is allowed to invoke.
 * @param enabled Whether the automation should be created enabled. Defaults to true when omitted.
 * @param ownerType Who owns the automation, fixed at creation. {@code user} keeps it private to its creator and its
 *     runs act as that user. {@code repository} makes it belong to the repository, visible to anyone with read access
 *     to the repository and maintainable by anyone with write access to the repository, and its runs act as the Copilot
 *     app rather than as a person. {@code repository} is rejected when repository ownership is not available for the
 *     repository. Supported values: {@code user}, {@code repository}. Defaults to {@code user}.
 * @param writeScope Controls which repository resources a run may modify. {@code trigger} limits writes to the resource
 *     that started the run, when one exists, and resources created during the run; {@code repository} allows writes to
 *     any resource in the repository. Supported values: {@code trigger}, {@code repository}. Defaults to
 *     {@code trigger}.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. Does not apply to
 *     interval-scheduled runs, which always run as the automation owner. Defaults to false.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/create-repo-automation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepoAutomationPayload(
        String name,
        @Nullable GitHubCreateRepoAutomationTriggers triggers,
        String prompt,
        @Nullable String model,
        @Nullable GitHubCreateRepoAutomationReasoningEffort reasoningEffort,
        @Nullable List<String> tools,
        @Nullable List<String> allowedCustomAgents,
        @Nullable Boolean enabled,
        @Nullable GitHubCreateRepoAutomationOwnerType ownerType,
        @Nullable GitHubCreateRepoAutomationWriteScope writeScope,
        @Nullable Boolean requireActorWritePermission) {
    public GitHubCreateRepoAutomationPayload {
        requireNonNull(name, "name");
        requireNonNull(prompt, "prompt");
        if (tools != null) {
            tools = List.copyOf(tools);
        }
        if (allowedCustomAgents != null) {
            allowedCustomAgents = List.copyOf(allowedCustomAgents);
        }
    }
}
