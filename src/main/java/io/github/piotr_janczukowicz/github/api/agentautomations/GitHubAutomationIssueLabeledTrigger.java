package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Issue labeled trigger. Shared by the 5 schemas that have this shape: automation_triggers_issue_labeled,
 * issue_labeled, item_triggers_issue_labeled, revision_snapshot_triggers_issue_labeled,
 * snapshot_triggers_issue_labeled.
 *
 * @param query A GitHub issue or pull request search query.
 * @param labels Label names matched case-insensitively against the label added by the event. Omit or use an empty array
 *     to match every added label.
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
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_labeled",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_labeled"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationIssueLabeledTrigger(
        @Nullable String query,
        @Nullable List<String> labels,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubAutomationIssueLabeledTrigger {
        requireMinLengthIfPresent(query, 1, "query");
        if (labels != null) {
            requireUniqueItems(labels, "labels");
        }
    }
}
