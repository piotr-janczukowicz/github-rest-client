package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An explicitly configured MCP tool with a server-qualified ID. Descriptions and input schemas are not discovered.
 *
 * @param id The tool identifier in &lt;server-name&gt;/&lt;tool-name&gt; format, for example sentry/list_issues.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-available-repo-automation-tools/200/properties/mcp_servers/items/properties/tools/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationToolsResponseMcpServerTool(String id) {
    public GitHubListAvailableRepoAutomationToolsResponseMcpServerTool {
        requireNonNull(id, "id");
    }
}
