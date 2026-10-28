package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Custom agents source repository.
 *
 * @param id The ID of the repository.
 * @param name The name of the repository.
 * @param fullName The full name of the repository (owner/name).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-copilot-custom-agents-source-for-enterprise/200/properties/repository")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepository(long id, String name, String fullName) {

    public GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepository {
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
    }
}
