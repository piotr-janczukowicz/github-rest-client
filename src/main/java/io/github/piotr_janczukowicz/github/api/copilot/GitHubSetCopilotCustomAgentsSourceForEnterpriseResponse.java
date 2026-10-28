package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Custom agents source configured for an enterprise.
 *
 * @param organization The organization that hosts the source repository.
 * @param repository The source repository.
 * @param ruleset The created or existing ruleset (if createRuleset was true).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-custom-agents-source-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse(
        @Nullable GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseOrganization organization,
        @Nullable GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRepository repository,
        @Nullable GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRuleset ruleset) {}
