package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Organization that hosts the custom agents source repository.
 *
 * @param id The ID of the organization.
 * @param login The login name of the organization.
 * @param avatarUrl The avatar URL of the organization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-custom-agents-source-for-enterprise/200/properties/organization")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseOrganization(
        @Nullable Long id, @Nullable String login, @Nullable String avatarUrl) {}
