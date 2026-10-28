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
 * Configuration of the automation completed trigger.
 *
 * @param automationIds Upstream Automation IDs in the same repository.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automations/200/items/properties/triggers/properties/automation_completed")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItemTriggersAutomationCompleted(
        List<String> automationIds,
        @Nullable GitHubListAutomationsItemTriggersAutomationCompletedWriteScope writeScope) {
    public GitHubListAutomationsItemTriggersAutomationCompleted {
        requireUniqueItems(requireSizeBetween(automationIds, 1, 3, "automationIds"), "automationIds");
    }
}
