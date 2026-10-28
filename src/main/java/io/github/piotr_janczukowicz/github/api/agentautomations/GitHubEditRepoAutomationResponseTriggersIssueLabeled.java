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
 * Configuration of the issue labeled trigger.
 *
 * @param query A GitHub issue or pull request search query.
 * @param labels Label names matched case-insensitively against the label added by the event. Omit or use an empty array
 *     to match every added label.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_labeled")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationResponseTriggersIssueLabeled(
        @Nullable String query,
        @Nullable List<String> labels,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueLabeledWriteScope writeScope) {
    public GitHubEditRepoAutomationResponseTriggersIssueLabeled {
        requireMinLengthIfPresent(query, 1, "query");
        if (labels != null) {
            requireUniqueItems(labels, "labels");
        }
    }
}
