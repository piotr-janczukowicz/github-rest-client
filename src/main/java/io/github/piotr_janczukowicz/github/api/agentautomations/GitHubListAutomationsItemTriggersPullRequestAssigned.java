package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the pull request assigned trigger.
 *
 * @param query A GitHub issue or pull request search query.
 * @param paths Gitignore-style changed-file path globs.
 * @param assignees Affected assignee logins matched case-insensitively.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_assigned")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItemTriggersPullRequestAssigned(
        @Nullable String query,
        @Nullable List<String> paths,
        @Nullable List<String> assignees,
        @Nullable GitHubListAutomationsItemTriggersPullRequestAssignedWriteScope writeScope) {
    public GitHubListAutomationsItemTriggersPullRequestAssigned {
        requireMinLengthIfPresent(query, 1, "query");
        if (paths != null) {
            requireNonEmpty(paths, "paths");
            for (final String path : paths) {
                requireMinLength(path, 1, "paths");
            }
        }
        if (assignees != null) {
            requireUniqueItems(requireNonEmpty(assignees, "assignees"), "assignees");
        }
    }
}
