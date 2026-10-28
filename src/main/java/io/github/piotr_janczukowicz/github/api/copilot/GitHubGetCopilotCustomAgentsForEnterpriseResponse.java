package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom agents defined in the source repository of an enterprise.
 *
 * @param customAgents List of custom agents defined in the repository. Null when no source repository is configured.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-copilot-custom-agents-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCopilotCustomAgentsForEnterpriseResponse(
        @Nullable List<GitHubCopilotCustomAgent> customAgents) {}
