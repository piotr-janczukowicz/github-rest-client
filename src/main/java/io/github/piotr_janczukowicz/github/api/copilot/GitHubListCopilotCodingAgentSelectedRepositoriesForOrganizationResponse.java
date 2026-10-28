package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories enabled for Copilot cloud agent in an organization.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/list-copilot-coding-agent-selected-repositories-for-organization/200")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse(
        int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse {
        requireNonNull(repositories, "repositories");
    }
}
