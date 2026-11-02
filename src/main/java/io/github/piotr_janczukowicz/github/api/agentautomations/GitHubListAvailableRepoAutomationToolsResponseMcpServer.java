package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A projection of a repository MCP server's configured tool allowlist, not live tool discovery or a guarantee of
 * runtime availability.
 *
 * @param name The server name from the repository MCP configuration.
 * @param tools Explicitly configured tools with server-qualified IDs, in configuration order. Empty when the server's
 *     allowlist is ["*"].
 * @param supportsWildcard Whether the effective repository configuration permits selecting all tools on this server
 *     using ["*"], including future tools. Mutually exclusive with a non-empty tools list.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-repo-automation-tools/200/properties/mcp_servers/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationToolsResponseMcpServer(
        String name,
        List<GitHubListAvailableRepoAutomationToolsResponseMcpServerTool> tools,
        boolean supportsWildcard) {
    public GitHubListAvailableRepoAutomationToolsResponseMcpServer {
        requireNonNull(name, "name");
        requireNonNull(tools, "tools");
    }
}
