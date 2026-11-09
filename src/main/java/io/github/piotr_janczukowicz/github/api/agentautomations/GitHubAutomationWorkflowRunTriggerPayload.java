package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Workflow run trigger payload. Shared by the 1 schemas that have this shape: workflow_run.
 *
 * @param workflows Workflow names matched against the name in each workflow YAML file.
 * @param conclusions Workflow conclusions to match. The current trigger supports failure.
 * @param query A GitHub issue or pull request search query.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}. Explicitly setting {@code repository} on create or edit returns 422 when policy validation is
 *     enabled and that policy applies.
 * @param allowCopilotTriggers When true, events Copilot creates, including from other automations and Copilot sessions,
 *     can start this automation through this trigger. Defaults to false. The Copilot coding agent app cannot turn it
 *     on: creating a trigger with true, or changing it from false to true, returns 422.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/workflow_run",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/workflow_run"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationWorkflowRunTriggerPayload(
        List<String> workflows,
        List<GitHubAutomationWorkflowRunConclusionInput> conclusions,
        @Nullable String query,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubAutomationWorkflowRunTriggerPayload {
        workflows = List.copyOf(requireNonEmpty(workflows, "workflows"));
        for (final String workflow : workflows) {
            requireMinLength(workflow, 1, "workflows");
        }
        conclusions = List.copyOf(requireSizeBetween(conclusions, 1, 1, "conclusions"));
        requireMinLengthIfPresent(query, 1, "query");
    }
}
