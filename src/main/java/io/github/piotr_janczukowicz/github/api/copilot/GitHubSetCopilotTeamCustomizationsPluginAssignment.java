package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set copilot team customizations plugin assignment.
 *
 * @param state The assignment state.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/copilot/set-copilot-team-customizations/requestBody/properties/pluginAssignments/additionalProperties")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCopilotTeamCustomizationsPluginAssignment(
        @Nullable GitHubSetCopilotTeamCustomizationsPluginAssignmentState state) {}
