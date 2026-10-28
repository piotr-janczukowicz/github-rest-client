package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Organization that hosts the custom agents source for an enterprise.
 *
 * @param organizationId The ID of the organization to use as the custom agents source.
 * @param createRuleset Whether to create a ruleset to protect agent definition files. Defaults to true.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-custom-agents-source-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotCustomAgentsSourceForEnterprisePayload(
        long organizationId, @Nullable Boolean createRuleset) {}
