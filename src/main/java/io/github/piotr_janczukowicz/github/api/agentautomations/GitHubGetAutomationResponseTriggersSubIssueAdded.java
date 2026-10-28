package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the sub issue added trigger.
 *
 * @param query A GitHub issue or pull request search query.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/get-automation/200/properties/triggers/properties/sub_issue_added")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationResponseTriggersSubIssueAdded(
        @Nullable String query, @Nullable GitHubGetAutomationResponseTriggersSubIssueAddedWriteScope writeScope) {
    public GitHubGetAutomationResponseTriggersSubIssueAdded {
        requireMinLengthIfPresent(query, 1, "query");
    }
}
