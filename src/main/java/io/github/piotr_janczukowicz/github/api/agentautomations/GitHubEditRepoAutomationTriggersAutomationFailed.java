package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Automation failed.
 *
 * @param automationIds Upstream Automation IDs in the same repository. An Automation cannot list itself, and a
 *     user-owned Automation can only be listed by its creator's user-owned Automations.
 * @param reasons Failure reasons to match. Omit to match only reported failures.
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
@CatalogSchema(
        "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/automation_failed")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationTriggersAutomationFailed(
        List<String> automationIds,
        @Nullable List<GitHubEditRepoAutomationTriggersAutomationFailedReason> reasons,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubEditRepoAutomationTriggersAutomationFailed {
        automationIds = List.copyOf(
                requireUniqueItems(requireSizeBetween(automationIds, 1, 3, "automationIds"), "automationIds"));
        if (reasons != null) {
            reasons = List.copyOf(requireUniqueItems(requireSizeBetween(reasons, 1, 3, "reasons"), "reasons"));
        }
    }
}
