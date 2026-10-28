package io.github.piotr_janczukowicz.github.api.agentautomations;

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
 * Configuration of the issue assigned trigger.
 *
 * @param query A GitHub issue or pull request search query.
 * @param assignees Affected assignee logins matched case-insensitively.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_assigned")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationResponseTriggersIssueAssigned(
        @Nullable String query,
        @Nullable List<String> assignees,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueAssignedWriteScope writeScope) {
    public GitHubEditRepoAutomationResponseTriggersIssueAssigned {
        requireMinLengthIfPresent(query, 1, "query");
        if (assignees != null) {
            requireUniqueItems(requireNonEmpty(assignees, "assignees"), "assignees");
        }
    }
}
