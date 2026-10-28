package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Enterprise teams to add to the Copilot subscription.
 *
 * @param selectedEnterpriseTeams Slugs of the enterprise teams to grant Copilot seats.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/add-copilot-seats-for-enterprise-teams/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddCopilotSeatsForEnterpriseTeamsPayload(List<String> selectedEnterpriseTeams) {

    public GitHubAddCopilotSeatsForEnterpriseTeamsPayload {
        selectedEnterpriseTeams = List.copyOf(requireNonEmpty(selectedEnterpriseTeams, "selectedEnterpriseTeams"));
    }
}
