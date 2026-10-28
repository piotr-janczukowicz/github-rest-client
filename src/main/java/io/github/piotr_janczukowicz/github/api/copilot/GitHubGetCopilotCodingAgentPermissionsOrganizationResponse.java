package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Copilot cloud agent permissions for an organization.
 *
 * @param enabledRepositories The policy for which repositories can use Copilot cloud agent.
 * @param selectedRepositoriesUrl The URL for the selected repositories endpoint. Only present when
 *     {@code enabled_repositories} is {@code selected}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/get-copilot-coding-agent-permissions-organization/200")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetCopilotCodingAgentPermissionsOrganizationResponse(
        GitHubCopilotCodingAgentEnabledRepositories enabledRepositories,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubGetCopilotCodingAgentPermissionsOrganizationResponse {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
