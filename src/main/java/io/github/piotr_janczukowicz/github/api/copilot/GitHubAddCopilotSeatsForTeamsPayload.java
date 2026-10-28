package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Teams to add to the Copilot subscription for an organization.
 *
 * @param selectedTeams List of team names within the organization to which to grant access to GitHub Copilot.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/add-copilot-seats-for-teams/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddCopilotSeatsForTeamsPayload(List<String> selectedTeams) {

    public GitHubAddCopilotSeatsForTeamsPayload {
        selectedTeams = List.copyOf(requireNonEmpty(selectedTeams, "selectedTeams"));
    }
}
