package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Pull request trigger payload. Shared by the 12 schemas that have this shape: pull_request_closed,
 * pull_request_converted_to_draft, pull_request_merge_conflict, pull_request_merged, pull_request_opened,
 * pull_request_ready_for_review, pull_request_reopened, pull_request_review_dismissed, pull_request_review_requested,
 * pull_request_review_thread_resolved, pull_request_review_thread_unresolved, pull_request_synchronize.
 *
 * @param query A GitHub issue or pull request search query.
 * @param paths Gitignore-style changed-file path globs.
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
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_closed",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_converted_to_draft",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_merge_conflict",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_merged",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_opened",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_ready_for_review",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_reopened",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_dismissed",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_requested",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_resolved",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_unresolved",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_synchronize",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_closed",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_converted_to_draft",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_merge_conflict",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_merged",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_opened",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_ready_for_review",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_reopened",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_dismissed",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_requested",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_resolved",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_unresolved",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_synchronize"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationPullRequestTriggerPayload(
        @Nullable String query,
        @Nullable List<String> paths,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubAutomationPullRequestTriggerPayload {
        requireMinLengthIfPresent(query, 1, "query");
        if (paths != null) {
            paths = List.copyOf(requireNonEmpty(paths, "paths"));
            for (final String path : paths) {
                requireMinLength(path, 1, "paths");
            }
        }
    }
}
