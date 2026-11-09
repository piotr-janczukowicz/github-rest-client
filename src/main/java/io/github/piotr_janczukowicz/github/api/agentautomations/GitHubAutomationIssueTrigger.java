package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue trigger. Shared by the 20 schemas that have this shape: automation_triggers_issue_closed,
 * automation_triggers_issue_opened, automation_triggers_issue_reopened, automation_triggers_sub_issue_added,
 * issue_closed, issue_opened, issue_reopened, item_triggers_issue_closed, item_triggers_issue_opened,
 * item_triggers_issue_reopened, item_triggers_sub_issue_added, revision_snapshot_triggers_issue_closed,
 * revision_snapshot_triggers_issue_opened, revision_snapshot_triggers_issue_reopened,
 * revision_snapshot_triggers_sub_issue_added, snapshot_triggers_issue_closed, snapshot_triggers_issue_opened,
 * snapshot_triggers_issue_reopened, snapshot_triggers_sub_issue_added, sub_issue_added.
 *
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
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_closed",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_opened",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_closed",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_opened",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/issue_closed",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/issue_opened",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_closed",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_opened",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/issue_closed",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/issue_opened",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_closed",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_opened",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/sub_issue_added",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_closed",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_opened",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_reopened",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/sub_issue_added"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationIssueTrigger(
        @Nullable String query,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubAutomationIssueTrigger {
        requireMinLengthIfPresent(query, 1, "query");
    }
}
