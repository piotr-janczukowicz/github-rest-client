package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Copilot cloud agent permissions to set for an organization.
 *
 * @param enabledRepositories The policy for which repositories can use Copilot cloud agent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/set-copilot-coding-agent-permissions-organization/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCopilotCodingAgentPermissionsOrganizationPayload(
        GitHubCopilotCodingAgentEnabledRepositories enabledRepositories) {

    public GitHubSetCopilotCodingAgentPermissionsOrganizationPayload {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
