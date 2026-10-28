package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Copilot cloud agent configuration for a repository.
 *
 * @param mcpConfiguration The user-supplied MCP server configuration for the repository, as a free-form JSON object.
 *     This will be {@code null} if no configuration has been set.
 * @param enabledTools The enabled review tools for Copilot cloud agent.
 * @param requireActionsWorkflowApproval Whether Actions workflow approval is required for Copilot cloud agent pull
 *     requests.
 * @param isFirewallEnabled Whether the firewall is enabled.
 * @param isFirewallRecommendedAllowlistEnabled Whether the firewall recommended allowlist is enabled.
 * @param customAllowlist A list of custom allowlist entries, as hosts or URLs, that the firewall will allow the Copilot
 *     cloud agent to access.
 * @param isAutomationsEnabled Whether automations are enabled in this repository.
 * @param requireWriteAccessForAutomationTriggers Whether write access is required for automation triggers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/get-copilot-cloud-agent-configuration/200")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetCopilotCloudAgentConfigurationResponse(
        @Nullable Map<String, Object> mcpConfiguration,
        GitHubCopilotCloudAgentEnabledTools enabledTools,
        boolean requireActionsWorkflowApproval,
        boolean isFirewallEnabled,
        boolean isFirewallRecommendedAllowlistEnabled,
        List<String> customAllowlist,
        boolean isAutomationsEnabled,
        boolean requireWriteAccessForAutomationTriggers) {

    public GitHubGetCopilotCloudAgentConfigurationResponse {
        requireNonNull(enabledTools, "enabledTools");
        requireNonNull(customAllowlist, "customAllowlist");
    }
}
