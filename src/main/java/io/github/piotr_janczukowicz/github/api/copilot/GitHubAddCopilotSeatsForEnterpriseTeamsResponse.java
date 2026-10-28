package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Result of adding Copilot seats for enterprise teams.
 *
 * @param seatsCreated Seats created.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/add-copilot-seats-for-enterprise-teams/201")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddCopilotSeatsForEnterpriseTeamsResponse(int seatsCreated) {}
