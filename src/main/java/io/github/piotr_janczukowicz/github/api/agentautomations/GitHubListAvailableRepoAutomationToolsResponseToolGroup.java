package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A group of tools available for use in automations
 *
 * @param id The unique identifier of the group.
 * @param title The human-readable name of the group.
 * @param tools The tools currently available in this group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-repo-automation-tools/200/properties/tool_groups/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationToolsResponseToolGroup(
        String id, String title, List<GitHubListAvailableRepoAutomationToolsResponseToolGroupTool> tools) {
    public GitHubListAvailableRepoAutomationToolsResponseToolGroup {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(tools, "tools");
    }
}
