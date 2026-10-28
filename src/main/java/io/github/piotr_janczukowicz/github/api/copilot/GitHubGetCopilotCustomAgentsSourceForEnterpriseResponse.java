package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Source repository of custom agents for an enterprise.
 *
 * @param organization The organization that hosts the source repository.
 * @param repository The source repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-copilot-custom-agents-source-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse(
        @Nullable GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganization organization,
        @Nullable GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepository repository) {}
