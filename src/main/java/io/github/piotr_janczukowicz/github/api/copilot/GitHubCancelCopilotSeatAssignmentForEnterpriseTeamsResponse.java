package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Result of cancelling Copilot seat assignments for enterprise teams.
 *
 * @param seatsCancelled Seats cancelled.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/cancel-copilot-seats-for-enterprise-teams/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse(int seatsCancelled) {}
