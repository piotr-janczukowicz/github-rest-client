package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories to enable for Copilot cloud agent in an organization.
 *
 * @param selectedRepositoryIds List of repository IDs to enable for Copilot cloud agent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/set-copilot-coding-agent-selected-repositories-for-organization/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
