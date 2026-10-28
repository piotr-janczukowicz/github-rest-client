package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Ruleset that protects agent definition files.
 *
 * @param id The ID of the ruleset.
 * @param name The name of the ruleset.
 * @param enforcement The enforcement level of the ruleset.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-custom-agents-source-for-enterprise/200/properties/ruleset")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRuleset(
        @Nullable Long id, @Nullable String name, @Nullable String enforcement) {}
