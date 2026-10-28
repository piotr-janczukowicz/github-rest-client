package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Users to add to the Copilot subscription for an organization.
 *
 * @param selectedUsernames The usernames of the organization members to be granted access to GitHub Copilot.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/add-copilot-seats-for-users/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddCopilotSeatsForUsersPayload(List<String> selectedUsernames) {

    public GitHubAddCopilotSeatsForUsersPayload {
        selectedUsernames = List.copyOf(requireNonEmpty(selectedUsernames, "selectedUsernames"));
    }
}
