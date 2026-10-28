package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Custom agents source repository.
 *
 * @param id The ID of the repository.
 * @param name The name of the repository.
 * @param fullName The full name of the repository (owner/name).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-custom-agents-source-for-enterprise/200/properties/repository")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRepository(
        @Nullable Long id, @Nullable String name, @Nullable String fullName) {}
