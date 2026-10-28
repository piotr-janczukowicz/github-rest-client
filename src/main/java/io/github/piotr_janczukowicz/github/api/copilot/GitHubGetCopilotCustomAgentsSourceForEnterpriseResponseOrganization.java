package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Organization that hosts the custom agents source repository.
 *
 * @param id The ID of the organization.
 * @param login The login name of the organization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-copilot-custom-agents-source-for-enterprise/200/properties/organization")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganization(long id, String login) {

    public GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganization {
        requireNonNull(login, "login");
    }
}
