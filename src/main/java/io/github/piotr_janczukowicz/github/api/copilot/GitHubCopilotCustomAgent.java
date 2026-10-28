package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Custom agent defined in the source repository.
 *
 * @param name The display name of the custom agent (derived from filename).
 * @param filePath The path to the agent definition file.
 * @param url The URL to view the agent definition file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-copilot-custom-agents-for-enterprise/200/properties/custom_agents/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCopilotCustomAgent(
        @Nullable String name,
        @Nullable String filePath,
        @Nullable String url) {}
