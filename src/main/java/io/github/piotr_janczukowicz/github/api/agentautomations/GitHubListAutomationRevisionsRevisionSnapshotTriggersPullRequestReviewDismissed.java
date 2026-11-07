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
 * Pull request review dismissed.
 *
 * @param query A GitHub issue or pull request search query.
 * @param paths Gitignore-style changed-file path globs.
 * @param writeScope Sets the write scope for this trigger's runs. Omit to inherit the automation's effective scope
 *     (default {@code trigger}). Organization or repository policy may restrict the effective scope to {@code trigger}.
 *     Explicitly setting {@code repository} on create or edit returns 422 when policy validation is enabled and that
 *     policy applies.
 * @param allowCopilotTriggers When true, events Copilot creates, including from other automations and Copilot sessions,
 *     can start this automation through this trigger. Defaults to false. The Copilot coding agent app cannot turn it
 *     on: creating a trigger with true, or changing it from false to true, returns 422.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/pull_request_review_dismissed")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewDismissed(
        @Nullable String query,
        @Nullable List<String> paths,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewDismissed {
        requireMinLengthIfPresent(query, 1, "query");
        if (paths != null) {
            requireNonEmpty(paths, "paths");
            for (final String path : paths) {
                requireMinLength(path, 1, "paths");
            }
        }
    }
}
