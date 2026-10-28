package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A CMC-controlled presentation category for Automation tools.
 *
 * @param id A presentation-category identifier controlled by CMC.
 * @param title The human-readable group title.
 * @param tools The tools currently presented in this group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-automation-tools/200/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationToolsItem(
        String id, String title, List<GitHubListAvailableAutomationToolsItemTool> tools) {
    public GitHubListAvailableAutomationToolsItem {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(tools, "tools");
    }
}
