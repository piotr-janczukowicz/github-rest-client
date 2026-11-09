package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Alert trigger. Shared by the 15 schemas that have this shape: automation_triggers_code_scanning_alert,
 * automation_triggers_dependabot_alert, automation_triggers_secret_scanning_alert, code_scanning_alert,
 * dependabot_alert, item_triggers_code_scanning_alert, item_triggers_dependabot_alert,
 * item_triggers_secret_scanning_alert, revision_snapshot_triggers_code_scanning_alert,
 * revision_snapshot_triggers_dependabot_alert, revision_snapshot_triggers_secret_scanning_alert, secret_scanning_alert,
 * snapshot_triggers_code_scanning_alert, snapshot_triggers_dependabot_alert, snapshot_triggers_secret_scanning_alert.
 *
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
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/secret_scanning_alert",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/code_scanning_alert",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/dependabot_alert",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/secret_scanning_alert"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationAlertTrigger(
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {}
