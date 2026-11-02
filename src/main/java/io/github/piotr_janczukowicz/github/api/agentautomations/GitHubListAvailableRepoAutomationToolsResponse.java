package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Tools available to the authenticated caller for configuring automations in a repository.
 *
 * @param toolGroups Caller-filtered built-in automation tool groups.
 * @param mcpServers Configured repository MCP servers, sorted by name. Empty when repository MCP is disabled or
 *     unconfigured.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-repo-automation-tools/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationToolsResponse(
        List<GitHubListAvailableRepoAutomationToolsResponseToolGroup> toolGroups,
        List<GitHubListAvailableRepoAutomationToolsResponseMcpServer> mcpServers) {
    public GitHubListAvailableRepoAutomationToolsResponse {
        requireNonNull(toolGroups, "toolGroups");
        requireNonNull(mcpServers, "mcpServers");
    }
}
