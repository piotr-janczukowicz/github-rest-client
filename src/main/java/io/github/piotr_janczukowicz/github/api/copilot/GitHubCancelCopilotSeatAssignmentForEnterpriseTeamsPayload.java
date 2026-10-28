package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Enterprise teams whose Copilot seats to cancel.
 *
 * @param selectedEnterpriseTeams Slugs of the enterprise teams to unassign.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/cancel-copilot-seats-for-enterprise-teams/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload(List<String> selectedEnterpriseTeams) {

    public GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload {
        selectedEnterpriseTeams = List.copyOf(requireNonEmpty(selectedEnterpriseTeams, "selectedEnterpriseTeams"));
    }
}
