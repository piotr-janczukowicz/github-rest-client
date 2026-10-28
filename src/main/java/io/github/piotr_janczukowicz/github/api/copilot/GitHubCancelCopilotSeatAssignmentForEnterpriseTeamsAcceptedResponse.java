package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Accepted cancellation of Copilot seat assignments for enterprise teams.
 *
 * @param message Status message of the accepted request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/cancel-copilot-seats-for-enterprise-teams/202")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse(
        @Nullable String message) {}
