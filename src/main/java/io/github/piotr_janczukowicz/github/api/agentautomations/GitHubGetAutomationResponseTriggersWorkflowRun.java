package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the workflow run trigger.
 *
 * @param workflows Workflow names matched against the name in each workflow YAML file.
 * @param conclusions Workflow conclusions to match. The current trigger supports failure.
 * @param query A GitHub issue or pull request search query.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/get-automation/200/properties/triggers/properties/workflow_run")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationResponseTriggersWorkflowRun(
        List<String> workflows,
        List<GitHubGetAutomationResponseTriggersWorkflowRunConclusion> conclusions,
        @Nullable String query,
        @Nullable GitHubGetAutomationResponseTriggersWorkflowRunWriteScope writeScope) {
    public GitHubGetAutomationResponseTriggersWorkflowRun {
        requireNonEmpty(workflows, "workflows");
        for (final String workflow : workflows) {
            requireMinLength(workflow, 1, "workflows");
        }
        requireSizeBetween(conclusions, 1, 1, "conclusions");
        requireMinLengthIfPresent(query, 1, "query");
    }
}
